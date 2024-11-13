package com.codeartist.component.cache.sample.service;

import com.codeartist.component.cache.sample.entity.Example;
import com.codeartist.component.cache.sample.entity.GenericExample;
import org.springframework.util.StopWatch;

/**
 * @author AiJiangnan
 * @date 2023/7/18
 */
public interface CacheService {

    String CACHE_KEY = "'CACHE_KEY'";
    String GENERIC_CACHE_KEY = "'GENERIC_CACHE_KEY'";
    String SPEL_CACHE_KEY = "'SPEL_CACHE_KEY'";
    String NULL_CACHE_KEY = "'NULL_CACHE_KEY'";

    Example cache(StopWatch stopWatch);

    GenericExample<Example> genericCache(StopWatch stopWatch);

    Example spelCache(Long id, StopWatch stopWatch);

    Example nullCache(StopWatch stopWatch);

    void deleteCache();

    void deleteGenericCache();

    void deleteSpelCache(Long id);

    void deleteNullCache();

    default void lock() {
    }
}
