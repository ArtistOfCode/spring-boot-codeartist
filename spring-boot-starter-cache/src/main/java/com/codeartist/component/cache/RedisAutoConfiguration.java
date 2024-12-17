package com.codeartist.component.cache;

import com.codeartist.component.cache.autoconfigure.RedisLettuceConnectionFactory;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.redis.SpringRedisCache;
import com.codeartist.component.core.support.metric.Metrics;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author AiJiangnan
 * @date 2023-11-16
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisTemplate.class)
public class RedisAutoConfiguration {

    @Bean
    @Primary
    LettuceConnectionFactory lettuceConnectionFactory(RedisProperties properties, ClientResources clientResources) {
        return new RedisLettuceConnectionFactory(properties, clientResources).buildStandaloneConnectionFactory();
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Cache defaultRedisCache(StringRedisTemplate stringRedisTemplate,
                                   CacheProperties cacheProperties,
                                   Metrics metrics) {
        return new SpringRedisCache(stringRedisTemplate, cacheProperties, metrics);
    }
}
