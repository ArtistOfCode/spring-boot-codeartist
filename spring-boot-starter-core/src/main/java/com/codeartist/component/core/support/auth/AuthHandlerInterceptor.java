package com.codeartist.component.core.support.auth;


import com.codeartist.component.core.entity.Principal;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AiJiangnan
 * @date 2024/12/18
 */
public class AuthHandlerInterceptor implements HandlerInterceptor {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ApiRouteProperties apiRouteProperties;
    @Autowired
    private AuthContext authContext;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (CollectionUtils.isEmpty(apiRouteProperties.getRoutes())) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_FORBIDDEN);
        }

        if (needAuthRoute(request.getMethod(), request.getRequestURI())) {
            Principal principal = authContext.getPrincipal();
            if (principal == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_UNAUTHORIZED);
            }
            return true;
        }
        return true;
    }

    private boolean needAuthRoute(String method, String uri) {

        return apiRouteProperties.getRoutes().stream()
                .filter(r -> (CollectionUtils.isEmpty(r.getMethods()) || r.getMethods().contains(method))
                        && r.getPattern().stream().anyMatch(p -> pathMatcher.match(p, uri)))
                .map(ApiRouteProperties.ApiRoute::getAuth)
                .findFirst()
                .orElse(true);
    }
}
