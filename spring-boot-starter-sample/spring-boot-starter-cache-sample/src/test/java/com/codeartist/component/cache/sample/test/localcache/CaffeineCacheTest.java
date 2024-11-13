package com.codeartist.component.cache.sample.test.localcache;

import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.sample.test.AbstractSpringRunnerTests;
import com.codeartist.component.cache.sample.test.bean.Demo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.UUID;

/**
 * Caffeine本地缓存测试
 *
 * @author J.N.AI
 * @date 2023-11-16
 */
public class CaffeineCacheTest extends AbstractSpringRunnerTests {

    @Autowired
    private LocalCache localCache;

    @Test
    public void get() {
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        // get -> set -> get -> del -> get
        String data = localCache.get(key);
        Assertions.assertNull(data);

        localCache.set(key, value);
        data = localCache.get(key);
        Assertions.assertEquals(value, data);

        localCache.delete(key);
        data = localCache.get(key);
        Assertions.assertNull(data);
    }

    @Test
    public void getValueLoader() {
        StopWatch stopWatch = new StopWatch();
        String key = UUID.randomUUID().toString();
        Demo value = Demo.builder().id(1L).name("AiJiangnan").age(28).build();

        Demo data = localCache.get(key, () -> this.doBusiness(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);

        data = localCache.get(key, () -> this.doBusiness(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertEquals(value, data);
    }

    @Test
    public void getNullLoader() {
        StopWatch stopWatch = new StopWatch();
        String key = UUID.randomUUID().toString();

        Demo data = localCache.get(key, () -> doBusinessNull(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertNull(data);

        data = localCache.get(key, () -> this.doBusinessNull(stopWatch));
        Assertions.assertEquals(1, stopWatch.getTaskCount());
        Assertions.assertNull(data);
    }

    private Demo doBusiness(StopWatch stopWatch) {
        stopWatch.start();
        Demo demo = Demo.builder().id(1L).name("AiJiangnan").age(28).build();
        stopWatch.stop();
        return demo;
    }

    private Demo doBusinessNull(StopWatch stopWatch) {
        stopWatch.start();
        stopWatch.stop();
        return null;
    }
}
