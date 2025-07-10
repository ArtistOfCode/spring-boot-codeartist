package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.core.support.auth.ApiAuthTemplate;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.auth.DefaultApiAuthTemplate;
import com.codeartist.component.core.support.auth.DefaultAuthContext;
import com.codeartist.component.core.support.props.AppProperties;
import com.codeartist.component.core.support.props.LocalCachePropertySource;
import com.codeartist.component.core.support.props.LocalPropertyLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * Web全局配置
 *
 * @author AiJiangnan
 * @date 2022/7/15
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties(AppProperties.class)
@Import({WebMvcAutoConfiguration.class, WebReactiveAutoConfiguration.class})
public class WebAutoConfiguration {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Caffeine<Object, Object> caffeineBuilder(SpringContext springContext,
                                                    @Value("${spring.caffeine.stats-enabled:false}") Boolean statsEnabled) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (Environments.PROD.not() || statsEnabled) {
            builder.recordStats();
        }
        return builder;
    }

    @Bean
    @ConditionalOnBean(LocalPropertyLoader.class)
    public LocalCachePropertySource localCachePropertySource(LocalPropertyLoader loader) {
        return new LocalCachePropertySource(loader.getCache());
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthContext authContext() {
        return new DefaultAuthContext();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiAuthTemplate apiAuthTemplate() {
        return new DefaultApiAuthTemplate();
    }
}
