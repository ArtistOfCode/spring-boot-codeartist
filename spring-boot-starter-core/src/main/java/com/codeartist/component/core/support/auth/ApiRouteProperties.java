package com.codeartist.component.core.support.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * API路由配置类
 *
 * @author AiJiangnan
 * @date 2024/2/6
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.api.router")
public class ApiRouteProperties {

    private List<ApiRoute> routes;

    @Getter
    @Setter
    public static class ApiRoute {

        private String service;
        private String name;
        private List<String> pattern;
        private List<String> methods;
        private Boolean auth;
    }
}
