package com.codeartist.component.cache.aop;

import com.codeartist.component.cache.bean.CacheAction;
import com.codeartist.component.core.support.cache.CacheType;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作实体
 *
 * @author AiJiangnan
 * @date 2023-12-01
 */
@Getter
@Setter
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
