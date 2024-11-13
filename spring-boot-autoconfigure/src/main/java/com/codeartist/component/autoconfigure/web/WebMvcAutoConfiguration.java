package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.autoconfigure.swagger.SwaggerAutoConfiguration;
import com.codeartist.component.core.web.ClientExceptionHandler;
import com.codeartist.component.core.web.ServerExceptionHandler;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Web MVC 配置
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
@Configuration(proxyBeanMethods = false)
@Import(SwaggerAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcAutoConfiguration {

    @Bean
    public ServerExceptionHandler serverExceptionHandler() {
        return new ServerExceptionHandler();
    }

    @Bean
    public ClientExceptionHandler clientExceptionHandler() {
        return new ClientExceptionHandler();
    }

    /**
     * Jackson序列化配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(JacksonProperties properties) {
        return builder -> {
            // Long转字符串
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            // LocalDateTime使用DateTime格式化
            if (properties.getDateFormat() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(properties.getDateFormat());
                builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
                builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            }
        };
    }
}
