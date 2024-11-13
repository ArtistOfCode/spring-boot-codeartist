package com.codeartist.component.cache.sample.service;

import com.codeartist.component.cache.sample.entity.Example;
import com.codeartist.component.cache.sample.entity.GenericExample;
import com.codeartist.component.core.support.cache.annotation.Cache;
import com.codeartist.component.core.support.cache.annotation.CacheDelete;
import com.codeartist.component.core.support.cache.annotation.CacheLock;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author AiJiangnan
 * @date 2022/8/18
 */
@Service
public class RedisCacheService implements CacheService {

    @Override
    @Cache(key = CACHE_KEY, timeout = 3, timeUnit = TimeUnit.MINUTES)
    public Example cache(StopWatch stopWatch) {
        stopWatch.start(UUID.randomUUID().toString());
        Example example = new Example();
        example.setName("AiJiangnan");
        example.setAge(27L);
        example.setPercent(new BigDecimal("100.8"));
        stopWatch.stop();
        return example;
    }

    @Override
    @Cache(key = GENERIC_CACHE_KEY, timeout = 3, timeUnit = TimeUnit.MINUTES)
    public GenericExample<Example> genericCache(StopWatch stopWatch) {
        stopWatch.start(UUID.randomUUID().toString());
        Example example = new Example();
        example.setName("AiJiangnan");
        example.setAge(27L);
        example.setPercent(new BigDecimal("100.8"));
        GenericExample<Example> genericExample = new GenericExample<>();
        genericExample.setName(example.getName());
        genericExample.setData(example);
        stopWatch.stop();
        return genericExample;
    }

    @Override
    @Cache(key = SPEL_CACHE_KEY, timeout = 3, timeUnit = TimeUnit.MINUTES)
    public Example spelCache(Long id, StopWatch stopWatch) {
        stopWatch.start(UUID.randomUUID().toString());
        Example example2 = new Example();
        example2.setName("AiJiangnan");
        example2.setAge(26L);
        Example example = new Example();
        example.setName("AiJiangnan");
        example.setAge(27L);
        example.setPercent(new BigDecimal("100.8"));
        example.setExample(example2);
        stopWatch.stop();
        return example;
    }

    @Override
    @Cache(key = NULL_CACHE_KEY, timeout = 3, timeUnit = TimeUnit.MINUTES)
    public Example nullCache(StopWatch stopWatch) {
        stopWatch.start(UUID.randomUUID().toString());
        stopWatch.stop();
        return null;
    }

    @Override
    @CacheDelete(key = CACHE_KEY)
    public void deleteCache() {
    }

    @Override
    @CacheDelete(key = GENERIC_CACHE_KEY)
    public void deleteGenericCache() {
    }

    @Override
    @CacheDelete(key = SPEL_CACHE_KEY)
    public void deleteSpelCache(Long id) {
    }

    @Override
    @CacheDelete(key = CacheService.NULL_CACHE_KEY)
    public void deleteNullCache() {
    }

    @Override
    @CacheLock(key = "'TEST_CACHE_LOCK'", timeout = 10)
    public void lock() {
    }
}
