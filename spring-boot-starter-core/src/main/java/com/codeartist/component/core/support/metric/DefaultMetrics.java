package com.codeartist.component.core.support.metric;

import java.util.function.ToDoubleFunction;

/**
 * 默认指标接口实现
 *
 * @author AiJiangnan
 * @date 2022/11/30
 */
public class DefaultMetrics implements Metrics {

    @Override
    public void counter(String name, String... tags) {
    }

    @Override
    public <T> void gauge(String name, T obj, ToDoubleFunction<T> valueFunction, String... tags) {
    }

    @Override
    public void gauge(String name, double value, String... tags) {
    }

    @Override
    public void summary(String name, double amount, String... tags) {
    }
}
