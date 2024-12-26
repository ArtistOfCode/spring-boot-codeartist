package com.codeartist.component.autoconfigure.cache;


import com.codeartist.component.cache.aop.CacheInterceptor;
import com.codeartist.component.cache.aop.CacheOperationSource;
import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.cache.core.LocalCache;
import com.codeartist.component.core.support.aop.AnnotationPointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;

import java.util.Map;

/**
 * 缓存切面配置
 *
 * @author AiJiangnan
 * @date 2024/12/26
 */
@Configuration(proxyBeanMethods = false)
@Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)
public class CacheAopAutoConfiguration {

    @Bean
    @Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource cacheOperationSource() {
        return new CacheOperationSource();
    }

    @Bean
    @Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(CacheOperationSource cacheOperationSource,
                                             @Lazy Map<String, LocalCache> localCacheMap,
                                             @Lazy Map<String, Cache> cacheMap) {
        return new CacheInterceptor(cacheOperationSource, localCacheMap, cacheMap);
    }

    @Bean
    @Role(RootBeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor cachePointcutAdvisor(CacheOperationSource cacheOperationSource,
                                                CacheInterceptor cacheInterceptor) {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        advisor.setAdvice(cacheInterceptor);
        advisor.setPointcut(new AnnotationPointcut(cacheOperationSource));
        return advisor;
    }
}
