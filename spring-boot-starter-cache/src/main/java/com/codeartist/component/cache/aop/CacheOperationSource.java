package com.codeartist.component.cache.aop;

import com.codeartist.component.cache.bean.CacheAction;
import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.core.redis.RedisCache;
import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.codeartist.component.core.support.cache.annotation.Cache;
import com.codeartist.component.core.support.cache.annotation.CacheDelete;
import com.codeartist.component.core.support.cache.annotation.CacheLock;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author J.N.AI
 * @date 2023-12-01
 */
public class CacheOperationSource {

    private static final Collection<CacheOperation> NULL_CACHING_ATTRIBUTE = Collections.emptyList();
    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(3);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cache.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheDelete.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheLock.class);
    }

    private final Map<MethodClassKey, Collection<CacheOperation>> attributeCache = new ConcurrentHashMap<>(16);

    public Collection<CacheOperation> getCacheOperations(Method method, Class<?> targetClass) {
        MethodClassKey key = new MethodClassKey(method, targetClass);
        Collection<CacheOperation> cached = attributeCache.get(key);

        if (cached != null) {
            return (cached != NULL_CACHING_ATTRIBUTE ? cached : null);
        }

        Collection<CacheOperation> cacheOps = parseCacheAnnotations(method);
        if (cacheOps != null) {
            this.attributeCache.put(key, cacheOps);
        } else {
            this.attributeCache.put(key, NULL_CACHING_ATTRIBUTE);
        }
        return cacheOps;
    }

    private Collection<CacheOperation> parseCacheAnnotations(AnnotatedElement ae) {

        Collection<? extends Annotation> anns = AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS);

        if (anns.isEmpty()) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);
        anns.stream().filter(ann -> ann instanceof Cache)
                .forEach(ann -> ops.add(parseCacheAnnotation((Cache) ann)));
        anns.stream().filter(ann -> ann instanceof CacheDelete)
                .forEach(ann -> ops.add(parseCacheDeleteAnnotation((CacheDelete) ann)));
        anns.stream().filter(ann -> ann instanceof CacheLock)
                .forEach(ann -> ops.add(parseCacheLockAnnotation((CacheLock) ann)));
        return ops;
    }

    private CacheOperation parseCacheAnnotation(Cache ann) {
        CacheOperation cacheOperation = new CacheOperation();
        cacheOperation.setAction(CacheAction.CACHE);
        cacheOperation.setKey(ann.key());
        cacheOperation.setTimeout(ann.timeout());
        cacheOperation.setTimeUnit(ann.timeUnit());
        cacheOperation.setType(ann.type());
        cacheOperation.setCacheRef(defaultCacheRef(ann.cacheRef()));
        cacheOperation.setRedisCacheRef(defaultRedisCacheRef(ann.redisCacheRef()));
        return cacheOperation;
    }

    private CacheOperation parseCacheDeleteAnnotation(CacheDelete ann) {
        CacheOperation cacheOperation = new CacheOperation();
        cacheOperation.setAction(CacheAction.CACHE_DELETE);
        cacheOperation.setKey(ann.key());
        cacheOperation.setType(ann.type());
        cacheOperation.setCacheRef(defaultCacheRef(ann.cacheRef()));
        cacheOperation.setRedisCacheRef(defaultRedisCacheRef(ann.redisCacheRef()));
        return cacheOperation;
    }

    private CacheOperation parseCacheLockAnnotation(CacheLock ann) {
        CacheOperation cacheOperation = new CacheOperation();
        cacheOperation.setAction(CacheAction.CACHE_LOCK);
        cacheOperation.setKey(ann.key());
        cacheOperation.setTimeout(ann.timeout());
        cacheOperation.setTimeUnit(ann.timeUnit());
        cacheOperation.setRedisCacheRef(defaultRedisCacheRef(ann.redisCacheRef()));
        return cacheOperation;
    }

    private String defaultCacheRef(String cacheRef) {
        if (GlobalConstants.DEFAULT.equals(cacheRef)) {
            return GlobalConstants.DEFAULT + LocalCache.class.getSimpleName();
        }
        return cacheRef;
    }

    private String defaultRedisCacheRef(String cacheRef) {
        if (GlobalConstants.DEFAULT.equals(cacheRef)) {
            return GlobalConstants.DEFAULT + RedisCache.class.getSimpleName();
        }
        return cacheRef;
    }
}
