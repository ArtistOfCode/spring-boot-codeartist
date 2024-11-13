package com.codeartist.component.cache.sample.test.cache;

import com.codeartist.component.cache.core.redis.RedisCache;
import com.codeartist.component.cache.sample.test.AbstractSpringRunnerTests;
import com.codeartist.component.cache.sample.test.bean.Demo;
import com.codeartist.component.cache.sample.test.bean.GenericDemo;
import com.codeartist.component.core.support.serializer.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Redis缓存测试
 *
 * @author J.N.AI
 * @date 2023-11-17
 */
class RedisCacheTest extends AbstractSpringRunnerTests {

    private static final Duration duration = Duration.ofMinutes(3);

    @Autowired
    private RedisCache redisCache;

    @Test
    void get() {
        String key = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        // get -> set -> get -> del -> get
        Demo data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);

        redisCache.set(key, value);
        data = redisCache.get(key, Demo.class);
        Assertions.assertEquals(value, data);

        redisCache.delete(key);
        data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);
    }

    @Test
    void getGeneric() {
        String key = UUID.randomUUID().toString();
        GenericDemo<Demo> value = GenericDemo.<Demo>builder()
                .name("AiJiangnan")
                .body(Demo.builder().id(1L).name("AiJiangnan").age(28).build())
                .build();

        // get -> set -> get -> del -> get
        GenericDemo<Demo> data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertNull(data);

        redisCache.set(key, value, duration);
        data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertEquals(value, data);

        redisCache.delete(key);
        data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertNull(data);
    }

    @Test
    void getValueLoader() {
        StopWatch stopWatch = new StopWatch();
        String key = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();

        Demo data = redisCache.get(key, Demo.class, () -> this.doBusiness(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);

        data = redisCache.get(key, Demo.class, () -> this.doBusiness(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);

        redisCache.delete(key);
        data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);
    }

    @Test
    void getValueLoaderGeneric() {
        StopWatch stopWatch = new StopWatch();
        String key = UUID.randomUUID().toString();
        GenericDemo<Demo> value = GenericDemo.<Demo>builder()
                .name("AiJiangnan")
                .body(Demo.builder().id(1L).name("AiJiangnan").age(28).build())
                .build();

        GenericDemo<Demo> data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        }, () -> this.doBusinessGeneric(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);

        data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        }, () -> this.doBusinessGeneric(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);

        redisCache.delete(key);
        data = redisCache.get(key, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertNull(data);
    }

    @Test
    void getValueLoaderNull() {
        StopWatch stopWatch = new StopWatch();
        String key = UUID.randomUUID().toString();

        Demo data = redisCache.get(key, duration, Demo.class, () -> this.doBusinessNull(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertNull(data);

        data = redisCache.get(key, duration, Demo.class, () -> this.doBusinessNull(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertNull(data);

        redisCache.delete(key);
        data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);
    }

    @Test
    void getHash() {
        String key = UUID.randomUUID().toString();
        String hashKey = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        // get -> set -> get -> del -> get
        Demo data = redisCache.getHash(key, hashKey, Demo.class);
        Assertions.assertNull(data);

        redisCache.setHash(key, hashKey, value);
        data = redisCache.getHash(key, hashKey, Demo.class);
        Assertions.assertEquals(value, data);

        redisCache.delete(key, hashKey);
        data = redisCache.getHash(key, hashKey, Demo.class);
        Assertions.assertNull(data);

        redisCache.delete(key);
        data = redisCache.getHash(key, hashKey, Demo.class);
        Assertions.assertNull(data);
    }

    @Test
    void getHashGeneric() {
        String key = UUID.randomUUID().toString();
        String hashKey = UUID.randomUUID().toString();
        GenericDemo<Demo> value = GenericDemo.<Demo>builder()
                .name("AiJiangnan")
                .body(Demo.builder().id(1L).name("AiJiangnan").age(28).build())
                .build();

        // get -> set -> get -> del -> get
        GenericDemo<Demo> data = redisCache.getHash(key, hashKey, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertNull(data);

        redisCache.setHash(key, hashKey, value);
        data = redisCache.getHash(key, hashKey, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertEquals(value, data);

        redisCache.delete(key);
        data = redisCache.getHash(key, hashKey, new TypeRef<GenericDemo<Demo>>() {
        });
        Assertions.assertNull(data);
    }

    @Test
    void expire() throws InterruptedException {
        String key = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        // get -> set -> expire -> get
        Demo data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);

        redisCache.set(key, value);
        redisCache.expire(key, Duration.ofSeconds(1));
        data = redisCache.get(key, Demo.class);
        Assertions.assertEquals(value, data);

        TimeUnit.SECONDS.sleep(1);
        data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);
    }

    @Test
    void exist() {
        String key = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        // get -> exist -> set -> get -> exist -> delete -> exist
        Demo data = redisCache.get(key, Demo.class);
        Assertions.assertNull(data);

        boolean exist = redisCache.exist(key);
        Assertions.assertFalse(exist);

        redisCache.set(key, value);
        data = redisCache.get(key, Demo.class);
        Assertions.assertEquals(value, data);

        exist = redisCache.exist(key);
        Assertions.assertTrue(exist);

        redisCache.delete(key);
        exist = redisCache.exist(key);
        Assertions.assertFalse(exist);
    }

    @Test
    void inc() {
        String key = UUID.randomUUID().toString();
        // get -> inc -> inc -> inc -> delete -> exist
        Long data = redisCache.get(key, Long.class);
        Assertions.assertNull(data);

        long inc = redisCache.inc(key);
        Assertions.assertEquals(1, inc);
        inc = redisCache.inc(key);
        Assertions.assertEquals(2, inc);
        inc = redisCache.inc(key);
        Assertions.assertEquals(3, inc);

        redisCache.delete(key);
        boolean exist = redisCache.exist(key);
        Assertions.assertFalse(exist);
    }

    @Test
    void checkKey() {
        Assertions.assertThrows(Exception.class, () -> redisCache.get(null, String.class));
    }

    private Demo doBusiness(StopWatch stopWatch) {
        stopWatch.start();
        Demo demo = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        stopWatch.stop();
        return demo;
    }

    private GenericDemo<Demo> doBusinessGeneric(StopWatch stopWatch) {
        stopWatch.start();
        GenericDemo<Demo> genericDemo = GenericDemo.<Demo>builder()
                .name("AiJiangnan")
                .body(Demo.builder().id(1L).name("AiJiangnan").age(28).build())
                .build();
        stopWatch.stop();
        return genericDemo;
    }

    private Demo doBusinessNull(StopWatch stopWatch) {
        stopWatch.start();
        stopWatch.stop();
        return null;
    }
}