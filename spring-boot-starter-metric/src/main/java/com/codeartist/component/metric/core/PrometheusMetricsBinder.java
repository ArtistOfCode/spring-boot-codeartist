package com.codeartist.component.metric.core;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.metric.thread.ThreadPoolMetrics;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * Prometheus指标绑定
 *
 * @author AiJiangnan
 * @date 2021/5/22
 */
public class PrometheusMetricsBinder implements ApplicationListener<ApplicationStartedEvent> {

    private final Metrics metrics;

    public PrometheusMetricsBinder(Metrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Map<String, ThreadPoolTaskExecutor> beans = applicationContext.getBeansOfType(ThreadPoolTaskExecutor.class);
        beans.forEach((beanName, bean) -> new ThreadPoolMetrics(beanName, bean).bindTo(metrics));
    }
}
