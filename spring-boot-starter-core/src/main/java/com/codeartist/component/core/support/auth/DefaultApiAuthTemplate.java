package com.codeartist.component.core.support.auth;


import com.codeartist.component.core.support.auth.ApiRouteProperties.ApiRoute;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口权限控制默认实现
 *
 * @author AiJiangnan
 * @date 2025/3/11
 */
@Getter
public class DefaultApiAuthTemplate implements ApiAuthTemplate {

    private final static PathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private AuthContext authContext;
    @Autowired
    private ApiRouteProperties apiRouteProperties;

    @Override
    public boolean needVerify(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(getApiRouteProperties().getRoutes())) {
            return true;
        }

        String method = request.getMethod();
        String uri = request.getRequestURI();

        return getApiRouteProperties().getRoutes().stream()
                .filter(r -> this.matchMethod(r, method))
                .filter(r -> this.matchUri(r, uri))
                .map(ApiRoute::getAuth)
                .findFirst()
                .orElse(true);
    }

    private boolean matchMethod(ApiRoute route, String method) {
        return CollectionUtils.isEmpty(route.getMethods()) || route.getMethods().contains(method);
    }

    private boolean matchUri(ApiRoute route, String uri) {
        return route.getPattern().stream().anyMatch(p -> pathMatcher.match(p, uri));
    }
}
