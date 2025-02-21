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

    /**
     * 缓存动作类型
     */
    private CacheAction action;

    /**
     * 缓存类型
     */
    private CacheType type;

    /**
     * 缓存键
     */
    private String key;

    /**
     * 缓存过期时间
     */
    private int timeout;

    /**
     * 缓存过期时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 本地缓存Bean
     */
    private String cacheRef;

    /**
     * Redis缓存Bean
     */
    private String redisCacheRef;

    public Duration getDuration() {
        return Duration.ofMillis(timeUnit.toMillis(timeout));
    }
}
