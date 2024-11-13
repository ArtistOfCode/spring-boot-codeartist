package com.codeartist.component.mq.metric;

import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqMessage;

/**
 * MQ监控
 *
 * @author AiJiangnan
 * @date 2021/7/23
 */
public class MqMetrics {

    private static final String MQ_PRODUCER = "mq_producer";
    private static final String MQ_CONSUMER = "mq_consumer";
    private static final String MQ_CONSUMER_ERROR = "mq_consumer_error";
    private static final String TYPE = "type";
    private static final String GROUP = "group";
    private static final String TOPIC = "topic";

    private final Metrics metrics;

    public MqMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public void producer(MqMessage message) {
        metrics.counter(MQ_PRODUCER, TYPE, message.getType().name(), TOPIC, message.getTopic());
    }

    public void consumer(MqContext context, double amount) {
        metrics.summary(MQ_CONSUMER, System.currentTimeMillis() - amount,
                TYPE, context.getType().name(), GROUP, context.getGroup(), TOPIC, context.getTopic());
    }

    public void consumerError(MqContext context) {
        metrics.counter(MQ_CONSUMER_ERROR,
                TYPE, context.getType().name(), GROUP, context.getGroup(), TOPIC, context.getTopic());
    }
}
