package com.codeartist.component.autoconfigure.metrics;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.metric.core.MetricRegistry;
import com.codeartist.component.metric.core.PrometheusMetricsBinder;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * Micrometer监控自动注入
 *
 * @author AiJiangnan
 * @date 2021/5/22
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnClass({MeterRegistry.class, MetricRegistry.class, PrometheusMetricsBinder.class})
public class MetricsRegistryAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Metrics metrics(MeterRegistry meterRegistry) {
        return new MetricRegistry(meterRegistry);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PrometheusMetricsBinder prometheusMetricsBinder(Metrics metrics) {
        return new PrometheusMetricsBinder(metrics);
    }
}

