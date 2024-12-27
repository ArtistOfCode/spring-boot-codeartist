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

    /**
     * API路由列表
     */
    private List<ApiRoute> routes;

    @Getter
    @Setter
    public static class ApiRoute {

        /**
         * 服务名称
         */
        private String service;
        /**
         * 接口名称
         */
        private String name;
        /**
         * 接口URI匹配模式
         */
        private List<String> pattern;
        /**
         * 接口请求方法
         */
        private List<String> methods;
        /**
         * 是否验证登录权限
         */
        private Boolean auth;
    }
}
