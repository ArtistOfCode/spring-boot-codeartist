package com.codeartist.component.cache.core;

import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * 缓存抽象类
 *
 * @author AiJiangnan
 * @date 2021/5/25
 */
public abstract class AbstractLocalCache implements LocalCache {

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
            if (valueLoader != null) {
                T obj = valueLoader.get();
                doSet(key, (ValueWrapper<T>) () -> obj);
                return obj;
            } else {
                return null;
            }
        }
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

    protected void checkNull(Object key) {
        Assert.notNull(key, "Cache key is null.");
    }
}
