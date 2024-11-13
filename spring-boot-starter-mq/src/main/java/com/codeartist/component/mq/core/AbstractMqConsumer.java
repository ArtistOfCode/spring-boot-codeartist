package com.codeartist.component.mq.core;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.core.support.props.AppProperties;
import com.codeartist.component.mq.bean.MqProperties;
import com.codeartist.component.mq.exception.MqException;
import com.codeartist.component.mq.metric.MqMetrics;
import com.codeartist.component.mq.support.MqEventListenerFactory;
import com.codeartist.component.mq.trace.MqTracers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * MQ消费者抽象
 *
 * @author AiJiangnan
 * @date 2021/5/19
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqConsumer implements MqConsumer {

    private final MqType type;

    @Autowired
    private MqMetrics mqMetrics;
    @Autowired
    private MqTracers mqTracers;
    @Autowired
    private MqEventListenerFactory factory;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    protected MqProperties properties;
    @Autowired
    protected ThreadPoolTaskExecutor mqConsumerExecutor;

    protected abstract void doStart();

    protected abstract void doStop();

    protected abstract void doRegister(MqContext listener);

    @Override
    public void start() {
        factory.getListeners(type).forEach(this::doRegister);
        doStart();
        log.info("{} MQ container started.", type);
    }

    @Override
    public void stop() {
        doStop();
        log.info("{} MQ container stopped.", type);
    }

    @Override
    public void doPublish(MqContext message) {
        mqTracers.consumer(message, () -> {
            try {
                long start = System.currentTimeMillis();
                SpringContext.publishEvent(message);
                mqMetrics.consumer(message, start);
            } catch (Exception e) {
                mqMetrics.consumerError(message);
                throw new MqException(message, "MQ consumer error.", e);
            }
        });
    }

    protected String getApplicationName() {
        return appProperties.getName();
    }
}
