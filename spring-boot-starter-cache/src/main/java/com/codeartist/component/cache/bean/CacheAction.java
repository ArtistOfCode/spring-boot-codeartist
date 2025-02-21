package com.codeartist.component.cache.bean;

/**
 * 缓存操作类型
 *
 * @author AiJiangnan
 * @date 2023/7/15
 */
public enum CacheAction {
    /**
     * 缓存
     */
    CACHE,
    /**
     * 删除缓存
     */
    EVICT,
    /**
     * 分布式锁
     */
    LOCK
}
