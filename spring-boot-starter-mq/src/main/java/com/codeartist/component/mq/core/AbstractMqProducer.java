package com.codeartist.component.mq.core;

import com.codeartist.component.core.support.mq.MqHeaders;
import com.codeartist.component.core.support.mq.MqMessage;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.core.util.JSON;
import com.codeartist.component.mq.metric.MqMetrics;
import com.codeartist.component.mq.trace.MqTracers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * MQ生产者抽象
 *
 * @author AiJiangnan
 * @date 2021/5/19
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqProducer implements MqProducer {

    private final MqType type;

    @Autowired
    private MqMetrics mqMetrics;
    @Autowired
    private MqTracers mqTracers;

    protected abstract void doSend(MqMessage message);

    @Override
    public void send(MqMessage message) {
        String topic = message.getTopic();
        Assert.hasText(topic, this.type + " MQ topic is blank");
        message.setType(this.type);

        if (message.getHeaders() == null) {
            message.setHeaders(new MqHeaders());
        }

        mqTracers.producer(message, () -> {
            doSend(message);
            mqMetrics.producer(message);
        });
    }

    @Override
    public void init() {
        log.info("{} MQ producer init.", type);
        start();
    }

    @Override
    public MqType getType() {
        return this.type;
    }

    @Override
    public void start() {
        log.info("{} MQ producer started.", type);
    }

    @Override
    public void stop() {
        log.info("{} MQ producer stopped.", type);
    }

    protected <T> String serialize(T data) {
        return JSON.toJSONString(data);
    }
}
