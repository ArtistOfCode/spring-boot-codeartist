package com.codeartist.component.cache;

import com.codeartist.component.cache.aop.CacheOperationSource;
import com.codeartist.component.cache.autoconfigure.RedisLettuceConnectionFactory;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.redis.SpringRedisCache;
import com.codeartist.component.cache.support.RedisCaptchaTemplate;
import com.codeartist.component.core.support.captcha.CaptchaProperties;
import com.codeartist.component.core.support.metric.Metrics;
import io.lettuce.core.resource.ClientResources;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis缓存自动配置
 *
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

    @Bean(CacheOperationSource.DEFAULT_CACHE_BEAN_NAME)
    @ConditionalOnBean(StringRedisTemplate.class)
    public Cache defaultRedisCache(StringRedisTemplate stringRedisTemplate,
                                   CacheProperties cacheProperties,
                                   Metrics metrics) {
        return new SpringRedisCache(stringRedisTemplate, cacheProperties, metrics);
    }

    @Bean
    @ConditionalOnBean(Cache.class)
    public RedisCaptchaTemplate redisCaptchaTemplate(CaptchaProperties properties, Cache cache) {
        return new RedisCaptchaTemplate(properties, cache);
    }
}
