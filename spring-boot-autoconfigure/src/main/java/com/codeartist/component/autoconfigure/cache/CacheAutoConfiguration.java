package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.CaffeineAutoConfiguration;
import com.codeartist.component.cache.RedisAutoConfiguration;
import com.codeartist.component.cache.aop.CacheAnnotationPointcut;
import com.codeartist.component.cache.aop.CacheInterceptor;
import com.codeartist.component.cache.aop.CacheOperationSource;
import com.codeartist.component.cache.bean.CacheProperties;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.cache.core.redis.RedisCache;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import java.util.Map;

/**
 * 缓存组件
 *
 * @author AiJiangnan
 * @date 2019/4/19
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CacheProperties.class)
@EnableConfigurationProperties(CacheProperties.class)
@Import({CaffeineAutoConfiguration.class, RedisAutoConfiguration.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CacheAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource cacheOperationSource() {
        return new CacheOperationSource();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(CacheOperationSource cacheOperationSource,
                                             Map<String, LocalCache> localCacheMap,
                                             Map<String, Cache> cacheMap) {
        CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSource(cacheOperationSource);
        interceptor.setLocalCacheMap(localCacheMap);
        interceptor.setCacheMap(cacheMap);
        return interceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor cachePointcutAdvisor(CacheOperationSource cacheOperationSource,
                                                CacheInterceptor cacheInterceptor) {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        advisor.setAdvice(cacheInterceptor);
        advisor.setPointcut(new CacheAnnotationPointcut(cacheOperationSource));
        return advisor;
    }

    @Bean
    @ConditionalOnBean(RedisCache.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CaptchaConsumerAspect captchaConsumerAspect(RedisCache defaultRedisCache) {
        return new CaptchaConsumerAspect(defaultRedisCache);
    }
}
