package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.auth.ApiAuthTemplate;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.auth.DefaultApiAuthTemplate;
import com.codeartist.component.core.support.auth.DefaultAuthContext;
import com.codeartist.component.core.support.props.AppProperties;
import com.codeartist.component.core.support.props.LocalCachePropertySource;
import com.codeartist.component.core.support.props.LocalPropertyLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

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
