package com.codeartist.component.cache.sample.test.service;

import com.codeartist.component.cache.sample.service.CacheService;
import com.codeartist.component.cache.sample.test.AbstractSpringRunnerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;


/**
 * @author J.N.AI
 * @date 2023-12-01
 */
class RedisCacheServiceTest extends AbstractSpringRunnerTests {

    @Autowired
    private CacheService redisCacheService;

    @Test
    void cache() {
        final StopWatch stopWatch = new StopWatch();
        redisCacheService.cache(stopWatch);
        redisCacheService.cache(stopWatch);
        redisCacheService.cache(stopWatch);
        Assertions.assertEquals(stopWatch.getTaskCount(), 1);
    }
}