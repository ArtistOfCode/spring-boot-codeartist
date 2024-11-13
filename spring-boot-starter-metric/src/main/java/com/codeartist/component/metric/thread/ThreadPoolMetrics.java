package com.codeartist.component.metric.thread;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.metric.core.MetricBinder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池指标
 *
 * @author AiJiangnan
 * @date 2021/5/22
 */
@Getter
@Setter
public class ThreadPoolMetrics implements MetricBinder {

    private final String beanName;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public ThreadPoolMetrics(String beanName, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.beanName = beanName;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void bindTo(Metrics metrics) {
        registerThreadPoolMetrics(metrics);
    }

    private void registerThreadPoolMetrics(Metrics metrics) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();

        String[] tags = new String[]{"thread_name", beanName};
        metrics.gauge("thread_pool_task_count", threadPoolExecutor, ThreadPoolExecutor::getTaskCount, tags);
        metrics.gauge("thread_pool_completed_task_count", threadPoolExecutor, ThreadPoolExecutor::getCompletedTaskCount, tags);
        metrics.gauge("thread_pool_queue_size", threadPoolExecutor, t -> t.getQueue().size(), tags);
        metrics.gauge("thread_pool_active_count", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getActiveCount, tags);
        metrics.gauge("thread_pool_core_pool_size", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getCorePoolSize, tags);
        metrics.gauge("thread_pool_current_pool_size", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getPoolSize, tags);
        metrics.gauge("thread_pool_max_pool_size", threadPoolTaskExecutor, ThreadPoolTaskExecutor::getMaxPoolSize, tags);
    }
}
