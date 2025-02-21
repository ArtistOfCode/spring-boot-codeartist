package com.codeartist.component.cache.aop;

import com.codeartist.component.cache.bean.CacheAction;
import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.codeartist.component.core.support.aop.AnnotationOperationSource;
import com.codeartist.component.core.support.cache.annotation.Cache;
import com.codeartist.component.core.support.cache.annotation.CacheEvict;
import com.codeartist.component.core.support.cache.annotation.CacheLock;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 缓存注解操作源
 *
 * @author AiJiangnan
 * @date 2023-12-01
 */
public class CacheOperationSource extends AnnotationOperationSource<CacheOperation> {

    public static final String DEFAULT_LOCAL_CACHE_BEAN = GlobalConstants.DEFAULT + "LocalCache";
    public static final String DEFAULT_CACHE_BEAN_NAME = GlobalConstants.DEFAULT + "RedisCache";

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(3);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cache.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheEvict.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheLock.class);
    }

    @Override
    protected Set<Class<? extends Annotation>> getOperationAnns() {
        return CacheOperationSource.CACHE_OPERATION_ANNOTATIONS;
    }

    @Override
    protected Collection<CacheOperation> parseAnnotations(Collection<? extends Annotation> anns) {
        final Collection<CacheOperation> ops = new ArrayList<>(1);
        anns.stream().filter(ann -> ann instanceof Cache)
                .forEach(ann -> ops.add(parseCacheAnnotation((Cache) ann)));
        anns.stream().filter(ann -> ann instanceof CacheEvict)
                .forEach(ann -> ops.add(parseCacheDeleteAnnotation((CacheEvict) ann)));
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

    private CacheOperation parseCacheDeleteAnnotation(CacheEvict ann) {
        CacheOperation cacheOperation = new CacheOperation();
        cacheOperation.setAction(CacheAction.EVICT);
        cacheOperation.setKey(ann.key());
        cacheOperation.setType(ann.type());
        cacheOperation.setCacheRef(defaultCacheRef(ann.cacheRef()));
        cacheOperation.setRedisCacheRef(defaultRedisCacheRef(ann.redisCacheRef()));
        return cacheOperation;
    }

    private CacheOperation parseCacheLockAnnotation(CacheLock ann) {
        CacheOperation cacheOperation = new CacheOperation();
        cacheOperation.setAction(CacheAction.LOCK);
        cacheOperation.setKey(ann.key());
        cacheOperation.setTimeout(ann.timeout());
        cacheOperation.setTimeUnit(ann.timeUnit());
        cacheOperation.setRedisCacheRef(defaultRedisCacheRef(ann.redisCacheRef()));
        return cacheOperation;
    }

    private String defaultCacheRef(String cacheRef) {
        if (GlobalConstants.DEFAULT.equals(cacheRef)) {
            return DEFAULT_LOCAL_CACHE_BEAN;
        }
        return cacheRef;
    }

    private String defaultRedisCacheRef(String cacheRef) {
        if (GlobalConstants.DEFAULT.equals(cacheRef)) {
            return DEFAULT_CACHE_BEAN_NAME;
        }
        return cacheRef;
    }
}
