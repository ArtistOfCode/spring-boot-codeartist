package com.codeartist.component.autoconfigure.metrics;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.metric.core.MetricRegistry;
import com.codeartist.component.metric.core.PrometheusMetricsBinder;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Micrometer监控自动注入
 *
 * @author AiJiangnan
 * @date 2021/5/22
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MeterRegistry.class, MetricRegistry.class, PrometheusMetricsBinder.class})
public class MetricsRegistryAutoConfiguration {

    @Bean
    public Metrics metrics(MeterRegistry meterRegistry) {
        return new MetricRegistry(meterRegistry);
    }

    @Bean
    public PrometheusMetricsBinder prometheusMetricsBinder(Metrics metrics) {
        return new PrometheusMetricsBinder(metrics);
    }
}

