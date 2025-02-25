package com.codeartist.component.autoconfigure.web;

import com.codeartist.component.autoconfigure.auth.WebAuthAutoConfiguration;
import com.codeartist.component.autoconfigure.swagger.SwaggerAutoConfiguration;
import com.codeartist.component.core.web.ClientExceptionHandler;
import com.codeartist.component.core.web.ServerExceptionHandler;
import com.codeartist.component.core.web.WebMvcConfiguration;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Web MVC 配置
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
@Configuration(proxyBeanMethods = false)
@Import({WebMvcConfiguration.class, SwaggerAutoConfiguration.class, WebAuthAutoConfiguration.class})
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
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // Long转字符串
            builder.serializerByType(Long.class, ToStringSerializer.instance)
                    .serializerByType(Long.TYPE, ToStringSerializer.instance);
            // LocalDateTime使用DateTime格式化
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
                    .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME))
                    .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        };
    }
}
