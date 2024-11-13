package com.codeartist.component.metric.core;

import com.codeartist.component.core.support.metric.Metrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

import java.util.function.ToDoubleFunction;

/**
 * Prometheus指标监控
 *
 * @author AiJiangnan
 * @date 2021/7/23
 */
public class MetricRegistry implements Metrics {

    private final MeterRegistry meterRegistry;

    public MetricRegistry(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void counter(String name, String... tags) {
        meterRegistry.counter(name, tags).increment();
    }

    @Override
    public <T> void gauge(String name, T obj, ToDoubleFunction<T> valueFunction, String... tags) {
        meterRegistry.gauge(name, Tags.of(tags), obj, valueFunction);
    }

    @Override
    public void gauge(String name, double value, String... tags) {
        meterRegistry.gauge(name, Tags.of(tags), value);
    }

    @Override
    public void summary(String name, double amount, String[] tags) {
        meterRegistry.summary(name, Tags.of(tags)).record(amount);
    }
}
