package com.codeartist.component.autoconfigure.sleuth;

import brave.Tracer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 链路追踪组件
 *
 * @author AiJiangnan
 * @date 2021/9/7
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = {"spring.sleuth.enabled"}, matchIfMissing = true)
@Import(TraceAutoConfiguration.SleuthTraceAutoConfiguration.class)
public class TraceAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(Tracer.class)
    static class SleuthTraceAutoConfiguration {

    }
}
