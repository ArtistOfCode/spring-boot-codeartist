package com.codeartist.component.cache;

import com.codeartist.component.cache.aop.CacheOperationSource;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.core.caffeine.CaffeineCache;
import com.codeartist.component.core.support.metric.Metrics;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Caffeine配置
 *
 * @author AiJiangnan
 * @date 2023-11-16
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Caffeine.class)
public class CaffeineAutoConfiguration {

    @Bean
    public Cache<Object, Object> defaultCache(CacheProperties properties, ObjectProvider<Caffeine<Object, Object>> caffeineBuilder) {
        return caffeineBuilder.getObject()
                .maximumSize(properties.getCaffeine().getMaxSize())
                .build();
    }

    @Bean(CacheOperationSource.DEFAULT_LOCAL_CACHE_BEAN)
    public LocalCache defaultLocalCache(Cache<Object, Object> defaultCache, Metrics metrics) {
        return new CaffeineCache(defaultCache, metrics);
    }
}
