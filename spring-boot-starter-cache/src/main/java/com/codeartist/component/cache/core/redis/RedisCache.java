package com.codeartist.component.cache.core.redis;

import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.core.support.serializer.TypeRef;

import java.time.Duration;

/**
 * Redis缓存接口
 *
 * @author AiJiangnan
 * @date 2022/4/25
 */
public interface RedisCache extends Cache {

    boolean setNx(String key, Object data, Duration duration);

    <T> T getHash(String key, String hashKey, Class<T> clazz);

    <T> T getHash(String key, String hashKey, TypeRef<T> clazz);

    void setHash(String key, String hashKey, Object data);

    void delete(String key, String... hashKey);

    void expire(String key, Duration duration);

    boolean exist(String key);

    long inc(String key);
}
