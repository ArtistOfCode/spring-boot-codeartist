package com.codeartist.component.cache.core;

import com.codeartist.component.core.support.serializer.TypeRef;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * 缓存接口
 *
 * @author AiJiangnan
 * @date 2021/5/24
 */
public interface Cache {

    Duration INFINITE_DURATION = Duration.ZERO;

    <T> T get(String key, Class<T> clazz);

    <T> T get(String key, TypeRef<T> clazz);

    <T> T get(String key, Class<T> clazz, Supplier<T> valueLoader);

    <T> T get(String key, TypeRef<T> clazz, Supplier<T> valueLoader);

    <T> T get(String key, Duration duration, Class<T> clazz, Supplier<T> valueLoader);

    <T> T get(String key, Duration duration, TypeRef<T> clazz, Supplier<T> valueLoader);

    void set(String key, Object data);

    void set(String key, Object data, Duration duration);

    void delete(String key);
}
