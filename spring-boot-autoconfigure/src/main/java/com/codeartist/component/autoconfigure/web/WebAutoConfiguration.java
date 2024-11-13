package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.auth.DefaultAuthContext;
import com.codeartist.component.core.support.props.AppProperties;
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
    @ConditionalOnMissingBean(AuthContext.class)
    public AuthContext authContext() {
        return new DefaultAuthContext();
    }
}
