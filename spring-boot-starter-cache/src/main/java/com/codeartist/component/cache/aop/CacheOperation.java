package com.codeartist.component.cache.aop;

import com.codeartist.component.cache.bean.CacheAction;
import com.codeartist.component.core.support.cache.CacheType;
import lombok.Data;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author J.N.AI
 * @date 2023-12-01
 */
@Data
public class CacheOperation {

    private CacheAction action;

    private String key;

    private long timeout;

    private TimeUnit timeUnit;

    private CacheType type;

    private String cacheRef;

    private String redisCacheRef;

    public Duration getDuration() {
        return Duration.ofMillis(timeUnit.toMillis(timeout));
    }
}
