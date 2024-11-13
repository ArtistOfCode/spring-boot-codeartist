package com.codeartist.component.mq.trace;

import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqMessage;

/**
 * MQ链路
 *
 * @author AiJiangnan
 * @date 2023/8/1
 */
public class MqTracers {

    private static final String TOPIC = "Topic";


    public void producer(MqMessage message, Runnable runnable) {
    }

    public void consumer(MqContext context, Runnable runnable) {
    }
}
