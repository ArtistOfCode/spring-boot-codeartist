package com.codeartist.component.core.support.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限验证拦截器
 *
 * @author AiJiangnan
 * @date 2024/12/18
 */
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiAuthTemplate apiAuthTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (apiAuthTemplate.needVerify(request)) {
            apiAuthTemplate.verify();
        }

        return true;
    }
}
