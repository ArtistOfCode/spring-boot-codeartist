package com.codeartist.component.cache.aop;

import com.codeartist.component.cache.bean.CacheAction;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.core.redis.RedisCache;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BusinessException;
import com.codeartist.component.core.support.serializer.TypeRef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.codeartist.component.core.SpringContext.afterCommit;

/**
 * 缓存注解拦截器
 *
 * @author AiJiangnan
 * @date 2023-12-01
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CacheInterceptor implements MethodInterceptor {

    private final static SpelExpressionParser parser = new SpelExpressionParser();
    private final static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final CacheOperationSource cacheOperationSource;
    private final Map<String, LocalCache> localCacheMap;
    private final Map<String, Cache> cacheMap;

    @Override
    public Object invoke(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();

        Map<CacheAction, CacheOperation> ops = cacheOperationSource.getOperations(method, getTargetClass(target))
                .stream().collect(Collectors.toMap(CacheOperation::getAction, Function.identity()));

        CacheOperation cacheOperation = ops.get(CacheAction.CACHE);
        CacheOperation cacheDeleteOperation = ops.get(CacheAction.EVICT);
        CacheOperation cacheLockOperation = ops.get(CacheAction.LOCK);

        CacheOperationInvoker invoker = () -> {
            try {
                Object result = invocation.proceed();
                afterCommit(() -> invokeWithDeleteCache(invocation, cacheDeleteOperation));
                return result;
            } catch (Throwable ex) {
                throw new CacheOperationInvoker.ThrowableWrapper(ex);
            }
        };

        invoker = invokeWithLock(invoker, invocation, cacheLockOperation);

        return invokeWithCache(invoker, invocation, cacheOperation);
    }

    private Object invokeWithCache(CacheOperationInvoker invoker, MethodInvocation invocation, CacheOperation cacheOperation) {
        if (cacheOperation == null) {
            return invoker.invoke();
        }

        Object returnValue;
        String key = getExpressionKey(invocation, cacheOperation);
        Duration duration = cacheOperation.getDuration();
        Method method = invocation.getMethod();
        Supplier<Object> valueLoader = invoker::invoke;
        TypeRef<Object> returnTypeRef = getTypeRef(method);

        switch (cacheOperation.getType()) {
            case LOCAL:
                returnValue = getLocalCache(cacheOperation.getCacheRef()).get(key, valueLoader);
                break;
            case REDIS:
                returnValue = getCache(cacheOperation.getRedisCacheRef()).get(key, duration, returnTypeRef, valueLoader);
                break;
            case COMBINE:
                Cache cache = getCache(cacheOperation.getRedisCacheRef());
                LocalCache localCache = getLocalCache(cacheOperation.getCacheRef());
                returnValue = localCache.get(key, () -> cache.get(key, duration, returnTypeRef, invoker::invoke));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cacheOperation.getType());
        }

        return returnValue;
    }

    private void invokeWithDeleteCache(MethodInvocation invocation, CacheOperation cacheOperation) {
        if (cacheOperation == null) {
            return;
        }

        String key = getExpressionKey(invocation, cacheOperation);

        switch (cacheOperation.getType()) {
            case LOCAL:
                getLocalCache(cacheOperation.getCacheRef()).delete(key);
                break;
            case REDIS:
                getCache(cacheOperation.getRedisCacheRef()).delete(key);
                break;
            case COMBINE:
                getLocalCache(cacheOperation.getCacheRef()).delete(key);
                getCache(cacheOperation.getRedisCacheRef()).delete(key);
                break;
        }
    }

    private CacheOperationInvoker invokeWithLock(CacheOperationInvoker invoker, MethodInvocation invocation, CacheOperation cacheOperation) {
        if (cacheOperation == null) {
            return invoker;
        }

        String key = getExpressionKey(invocation, cacheOperation);
        Duration duration = cacheOperation.getDuration();
        RedisCache cache = ((RedisCache) getCache(cacheOperation.getCacheRef()));

        return () -> {
            boolean lock = cache.setNx(key, LocalDateTime.now(), duration);
            if (!lock) {
                throw new BusinessException(GlobalErrorCode.GLOBAL_SERVICE_BUSY_ERROR);
            }
            try {
                return invoker.invoke();
            } finally {
                cache.delete(key);
            }
        };
    }

    public String getExpressionKey(MethodInvocation invocation, CacheOperation cacheOperation) {
        String key = cacheOperation.getKey();
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();

        MethodBasedEvaluationContext context =
                new MethodBasedEvaluationContext(cacheOperation, method, arguments, parameterNameDiscoverer);
        return parser.parseExpression(key).getValue(context, String.class);
    }

    private LocalCache getLocalCache(String localCacheName) {
        LocalCache localCache = localCacheMap.get(localCacheName);
        Assert.notNull(localCache, "Local cache bean is null.");
        return localCache;
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheMap.get(cacheName);
        Assert.notNull(cache, "Redis cache bean is null.");
        return cache;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

    private TypeRef<Object> getTypeRef(Method method) {
        return new TypeRef<Object>() {
            @Override
            public Type getType() {
                return method.getGenericReturnType();
            }
        };
    }
}
