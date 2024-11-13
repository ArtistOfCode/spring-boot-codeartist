package com.codeartist.component.mq.core;

import com.codeartist.component.core.support.mq.MqContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * MQ消费者
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
public interface MqConsumer extends MqLifecycle, ApplicationRunner {

    void doPublish(MqContext message);

    @Override
    default void run(ApplicationArguments args) {
        start();
    }
}
