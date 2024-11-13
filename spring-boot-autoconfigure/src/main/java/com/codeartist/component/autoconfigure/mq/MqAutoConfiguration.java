package com.codeartist.component.autoconfigure.mq;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.mq.RedisMqAutoConfiguration;
import com.codeartist.component.mq.bean.MqProperties;
import com.codeartist.component.mq.metric.MqMetrics;
import com.codeartist.component.mq.support.MqEventListenerFactory;
import com.codeartist.component.mq.support.MqProducerListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 消息队列组件
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MqProperties.class)
@EnableConfigurationProperties(MqProperties.class)
@Import({RedisMqAutoConfiguration.class})
public class MqAutoConfiguration {

    /**
     * MQ生产者监听器
     */
    @Bean
    public MqProducerListener mqProducerListener() {
        return new MqProducerListener();
    }

    /**
     * MQ监控指标
     */
    @Bean
    public MqMetrics mqMetrics(Metrics metrics) {
        return new MqMetrics(metrics);
    }

    /**
     * MQ事件工厂
     */
    @Bean
    public MqEventListenerFactory mqEventListenerFactory() {
        return new MqEventListenerFactory();
    }

    /**
     * MQ消费线程池
     */
    @Bean
    public ThreadPoolTaskExecutor mqConsumerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("mq-consumer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
