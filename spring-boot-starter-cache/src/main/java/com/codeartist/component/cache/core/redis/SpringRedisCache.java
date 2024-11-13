package com.codeartist.component.cache.core.redis;

import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.core.support.metric.Metrics;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Redis缓存
 *
 * @author AiJiangnan
 * @date 2021/5/24
 */
public class SpringRedisCache extends AbstractRedisCache {

    private final StringRedisTemplate stringRedisTemplate;

    public SpringRedisCache(StringRedisTemplate stringRedisTemplate, CacheProperties cacheProperties, Metrics metrics) {
        super(cacheProperties, metrics);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected byte[] doGetRaw(String key) {
        return stringRedisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(rawKey(key)));
    }

    @Override
    protected void doSetRaw(String key, byte[] data, Duration duration) {
        stringRedisTemplate.execute((RedisCallback<?>) connection -> {
            if (INFINITE_DURATION.equals(duration)) {
                connection.set(rawKey(key), data);
                return null;
            }
            connection.pSetEx(rawKey(key), duration.toMillis(), data);
            return null;
        });
    }

    @Override
    protected boolean doSetRawNx(String key, byte[] data, Duration duration) {
        Boolean execute = (Boolean) stringRedisTemplate.execute((RedisCallback<Object>) connection ->
                connection.set(rawKey(key), data, Expiration.from(duration), SetOption.ifAbsent()));
        return Boolean.TRUE.equals(execute);
    }

    @Override
    protected byte[] doGetRaw(String key, String hashKey) {
        return stringRedisTemplate.execute(
                (RedisCallback<byte[]>) connection -> connection.hGet(rawKey(key), rawKey(hashKey)));
    }

    @Override
    protected void doSetRaw(String key, String hashKey, byte[] data) {
        stringRedisTemplate.execute((RedisCallback<?>) connection -> {
            connection.hSet(rawKey(key), rawKey(hashKey), data);
            return null;
        });
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void delete(String key, String... hashKey) {

        byte[][] rawHashKey = new byte[hashKey.length][];
        for (int i = 0; i < hashKey.length; i++) {
            rawHashKey[i] = rawKey(hashKey[i]);
        }

        stringRedisTemplate.execute((RedisCallback<?>) connection -> {
            connection.hDel(rawKey(key), rawHashKey);
            return null;
        });
    }

    @Override
    public void expire(String key, Duration duration) {
        stringRedisTemplate.expire(key, duration);
    }

    @Override
    public boolean exist(String key) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public long inc(String key) {
        Long increment = stringRedisTemplate.opsForValue().increment(key);
        return increment != null ? increment : 0;
    }

    private byte[] rawKey(String key) {
        return key.getBytes(StandardCharsets.UTF_8);
    }
}
