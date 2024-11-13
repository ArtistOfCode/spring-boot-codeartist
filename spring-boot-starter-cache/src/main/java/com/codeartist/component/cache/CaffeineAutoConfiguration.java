package com.codeartist.component.cache;

import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.core.caffeine.CaffeineCache;
import com.codeartist.component.core.support.metric.Metrics;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;

/**
 * Caffeine配置
 *
 * @author AiJiangnan
 * @date 2023-11-16
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Caffeine.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CaffeineAutoConfiguration {

    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Cache<Object, Object> defaultCache() {
        return Caffeine.newBuilder().maximumSize(1_000).build();
    }

    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LocalCache defaultLocalCache(Cache<Object, Object> defaultCache, Metrics metrics) {
        return new CaffeineCache(defaultCache, metrics);
    }
}
