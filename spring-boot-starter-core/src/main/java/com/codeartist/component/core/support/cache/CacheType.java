package com.codeartist.component.core.support.cache;

/**
 * 缓存类型
 *
 * @author AiJiangnan
 * @date 2023/7/15
 */
public enum CacheType {
    /**
     * 本地缓存
     */
    LOCAL,
    /**
     * Redis缓存
     */
    REDIS,
    /**
     * 二级缓存
     */
    COMBINE
}
