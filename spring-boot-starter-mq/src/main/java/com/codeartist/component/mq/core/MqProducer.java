package com.codeartist.component.mq.core;

import com.codeartist.component.core.support.mq.MqMessage;
import com.codeartist.component.core.support.mq.MqType;

/**
 * MQ生产者
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
public interface MqProducer extends MqLifecycle {

    MqType getType();

    void send(MqMessage message);
}
