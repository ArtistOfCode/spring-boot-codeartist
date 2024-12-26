package com.codeartist.component.autoconfigure.auth;

import com.codeartist.component.core.support.auth.ApiRouteProperties;
import com.codeartist.component.core.support.auth.AuthHandlerInterceptor;
import com.codeartist.component.core.web.WebMvcConfiguration;
import org.apache.catalina.CredentialHandler;
import org.apache.catalina.realm.MessageDigestCredentialHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

/**
 * Web权限自动配置
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ApiRouteProperties.class)
public class WebAuthAutoConfiguration {

    @Bean
    public CredentialHandler credentialHandler() throws NoSuchAlgorithmException {
        MessageDigestCredentialHandler handler = new MessageDigestCredentialHandler();
        handler.setAlgorithm("MD5");
        handler.setSaltLength(16);
        return handler;
    }

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }

    @Bean
    public WebMvcConfiguration webMvcConfiguration() {
        return new WebMvcConfiguration();
    }
}
