package com.codeartist.component.core.web;


import com.codeartist.component.core.support.auth.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author AiJiangnan
 * @date 2024/12/18
 */
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthHandlerInterceptor authHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor).addPathPatterns("/api/**");
    }
}
