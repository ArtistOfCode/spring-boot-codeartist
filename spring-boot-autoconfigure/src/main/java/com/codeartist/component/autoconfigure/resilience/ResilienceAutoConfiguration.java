package com.codeartist.component.autoconfigure.resilience;


import com.codeartist.component.core.web.ResilienceExceptionHandler;
import io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Resilience自动配置
 *
 * @author AiJiangnan
 * @date 2025/3/11
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RateLimiterProperties.class)
public class ResilienceAutoConfiguration {

    @Bean
    public ResilienceExceptionHandler resilienceExceptionHandler() {
        return new ResilienceExceptionHandler();
    }
}
