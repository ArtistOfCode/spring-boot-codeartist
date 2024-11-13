package com.codeartist.component.cache.core;

import com.codeartist.component.core.support.cache.CacheType;
import com.codeartist.component.core.support.metric.Metrics;

import java.util.function.Supplier;

/**
 * 缓存抽象类
 *
 * @author AiJiangnan
 * @date 2021/5/25
 */
public abstract class AbstractLocalCache extends AbstractCacheSupport implements LocalCache {

    public AbstractLocalCache(CacheType type, Metrics metrics) {
        super(type, metrics);
    }

    protected abstract <T> T doGet(Object key);

    protected abstract void doSet(Object key, Object data);

    @Override
    public <T> T get(Object key) {
        return get(key, null);
    }

    @Override
    public <T> T get(Object key, Supplier<T> valueLoader) {
        checkNull(key);
        ValueWrapper<T> data = doGet(key);
        if (data == null) {
            miss(key.toString());
            if (valueLoader != null) {
                T obj = valueLoader.get();
                doSet(key, (ValueWrapper<T>) () -> obj);
                return obj;
            } else {
                return null;
            }
        }
        hit(key.toString());
        return data.get();
    }

    @Override
    public void set(Object key, Object data) {
        doSet(key, (ValueWrapper<Object>) () -> data);
    }

    @FunctionalInterface
    protected interface ValueWrapper<T> {
        T get();
    }
}
