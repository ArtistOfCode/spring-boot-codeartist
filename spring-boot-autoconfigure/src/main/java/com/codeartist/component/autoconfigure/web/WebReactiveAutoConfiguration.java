package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.autoconfigure.swagger.SwaggerFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Web Reactive 配置
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
@Configuration(proxyBeanMethods = false)
@Import(SwaggerFluxAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebReactiveAutoConfiguration {

}
