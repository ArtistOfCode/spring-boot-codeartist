package com.codeartist.component.core.support.metric;

import java.util.function.ToDoubleFunction;

/**
 * 监控指标
 *
 * @author AiJiangnan
 * @date 2021/7/23
 */
public interface Metrics {

    void counter(String name, String... tags);

    <T> void gauge(String name, T obj, ToDoubleFunction<T> valueFunction, String... tags);

    void gauge(String name, double value, String... tags);

    void summary(String name, double amount, String... tags);
}
