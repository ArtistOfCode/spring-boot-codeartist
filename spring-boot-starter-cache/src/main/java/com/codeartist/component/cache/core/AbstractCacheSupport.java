package com.codeartist.component.cache.core;

import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.core.support.cache.CacheType;
import com.codeartist.component.core.support.metric.Metrics;
import org.springframework.util.Assert;

/**
 * 缓存支持
 *
 * @author AiJiangnan
 * @date 2023-11-15
 */
public abstract class AbstractCacheSupport {

    private static final String CACHE_HIT_METRIC = "cache_hit";
    private static final String CACHE_MISS_METRIC = "cache_miss";
    private static final String METRIC_TYPE = "type";
    private static final String METRIC_KEY = "key";

    private final CacheType type;
    private final Metrics metrics;

    public AbstractCacheSupport(CacheType type, Metrics metrics) {
        this.type = type;
        this.metrics = metrics;
    }

    protected void hit(String key) {
        metrics.counter(CACHE_HIT_METRIC,
                METRIC_TYPE, type.name(), METRIC_KEY, key.split(CacheProperties.DELIMITER)[0]);
    }

    protected void miss(String key) {
        metrics.counter(CACHE_MISS_METRIC,
                METRIC_TYPE, type.name(), METRIC_KEY, key.split(CacheProperties.DELIMITER)[0]);
    }

    protected void checkNull(Object key) {
        Assert.notNull(key, "Cache key is null.");
    }

    protected void checkKey(String key) {
        Assert.hasText(key, "Cache key is blank.");
    }
}
