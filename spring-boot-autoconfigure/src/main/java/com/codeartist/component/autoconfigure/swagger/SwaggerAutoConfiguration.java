package com.codeartist.component.autoconfigure.swagger;

import com.codeartist.component.core.annotation.Development;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.core.entity.enums.GlobalConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger自动配置（Web MVC）
 *
 * @author AiJiangnan
 * @date 2022/8/18
 */
@Development
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(org.springdoc.webmvc.ui.SwaggerConfig.class)
public class SwaggerAutoConfiguration {

    @Value(GlobalConstants.APPLICATION_NAME_KEY)
    private String appName;
    @Value(GlobalConstants.ROOT_PACKAGE_KEY)
    private String rootPackage;
    @Value(GlobalConstants.MODULE_PACKAGE_KEY)
    private List<String> modulePackages;

    @Bean
    public OpenAPI openAPI(Environment environment) {
        OpenAPI openAPI = new OpenAPI().info(new Info().title(appName).version("v1"));
        if (!environment.acceptsProfiles(Profiles.of(Environments.LOCAL.getProfile()))) {
            openAPI.extensions(Collections.singletonMap("basePath", "/api/" + appName));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        List<String> packages = new ArrayList<>();
        packages.add(parseController(rootPackage));
        if (!CollectionUtils.isEmpty(modulePackages)) {
            modulePackages.forEach(m -> packages.add(parseController(m)));
        }
        return GroupedOpenApi.builder()
                .group(appName)
                .packagesToScan(packages.toArray(new String[0]))
                .build();
    }

    private String parseController(String root) {
        return root + ".controller";
    }
}
