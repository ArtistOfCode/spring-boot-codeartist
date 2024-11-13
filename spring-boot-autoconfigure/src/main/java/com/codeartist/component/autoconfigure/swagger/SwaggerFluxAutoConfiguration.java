package com.codeartist.component.autoconfigure.swagger;

import com.codeartist.component.core.annotation.Development;
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.resource.ResourceResolverChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Swagger自动配置（Web Flux）
 *
 * @author AiJiangnan
 * @date 2022/8/18
 */
@Development
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(org.springdoc.webflux.ui.SwaggerConfig.class)
public class SwaggerFluxAutoConfiguration {

    private static final Set<String> NO_API_SERVICE = Collections.unmodifiableSet(new HashSet<String>(4) {
        {
            add("gateway");
            add("consul");
            add("nomad");
            add("nomad-client");
        }
    });

    @Bean
    public List<GroupedOpenApi> apis(SwaggerUiConfigProperties properties, DiscoveryClient discoveryClient) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        Set<SwaggerUrl> urls = discoveryClient.getServices().stream()
                .filter(this::excludeService)
                .map(this::parseSwaggerUrl)
                .collect(Collectors.toSet());
        properties.setUrls(urls);
        return groups;
    }

    private boolean excludeService(String service) {
        return !NO_API_SERVICE.contains(service);
    }

    private SwaggerUrl parseSwaggerUrl(String service) {
        return new SwaggerUrl(service, String.format("/api/%s%s", service, Constants.DEFAULT_API_DOCS_URL), service);
    }

    @Bean
    public SwaggerResourceResolver swaggerResourceResolver(SwaggerUiConfigProperties properties) {
        return new SwaggerResourceResolver(properties);
    }

    public static class SwaggerResourceResolver extends org.springdoc.webflux.ui.SwaggerResourceResolver {

        public SwaggerResourceResolver(SwaggerUiConfigProperties swaggerUiConfigProperties) {
            super(swaggerUiConfigProperties);
        }

        @Override
        public Mono<Resource> resolveResource(ServerWebExchange exchange, String requestPath,
                                              List<? extends Resource> locations, ResourceResolverChain chain) {
            Mono<Resource> resolved = chain.resolveResource(exchange, requestPath, locations);
            if (!Mono.empty().equals(resolved)) {
                return chain.resolveResource(exchange, requestPath, locations);
            }
            return resolved;
        }
    }
}
