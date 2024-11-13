package com.codeartist.component.cache.core;

import java.util.function.Supplier;

/**
 * 本地缓存接口
 *
 * @author AiJiangnan
 * @date 2021/5/24
 */
public interface LocalCache {

    <T> T get(Object key);

    <T> T get(Object key, Supplier<T> valueLoader);

    void set(Object key, Object data);

    void delete(Object key);
}
