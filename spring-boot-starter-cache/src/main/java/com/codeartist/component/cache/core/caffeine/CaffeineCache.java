package com.codeartist.component.cache.core.caffeine;

import com.codeartist.component.cache.core.AbstractLocalCache;
import com.codeartist.component.core.support.cache.CacheType;
import com.codeartist.component.core.support.metric.Metrics;
import com.github.benmanes.caffeine.cache.Cache;

/**
 * Caffeine缓存
 *
 * @author AiJiangnan
 * @date 2023/7/15
 */
public class CaffeineCache extends AbstractLocalCache {

    private final Cache<Object, Object> cache;

    public CaffeineCache(Cache<Object, Object> cache, Metrics metrics) {
        super(CacheType.LOCAL, metrics);
        this.cache = cache;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doGet(Object key) {
        return (T) cache.getIfPresent(key);
    }

    @Override
    protected void doSet(Object key, Object data) {
        cache.put(key, data);
    }

    @Override
    public void delete(Object key) {
        cache.invalidate(key);
    }
}
