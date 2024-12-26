package com.codeartist.component.cache.sample.test.service;

import com.codeartist.component.cache.sample.service.CacheService;
import com.codeartist.component.cache.sample.test.AbstractSpringRunnerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;


/**
 * @author AiJiangnan
 * @date 2023-12-01
 */
class LocalCacheServiceTest extends AbstractSpringRunnerTests {

    @Autowired
    private CacheService localCacheService;

    @Test
    void cache() {
        final StopWatch stopWatch = new StopWatch();
        localCacheService.cache(stopWatch);
        localCacheService.cache(stopWatch);
        localCacheService.cache(stopWatch);
        Assertions.assertEquals(1, stopWatch.getTaskCount());
    }
}