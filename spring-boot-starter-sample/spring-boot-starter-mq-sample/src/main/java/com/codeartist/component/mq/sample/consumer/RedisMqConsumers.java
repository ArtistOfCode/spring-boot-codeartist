package com.codeartist.component.mq.sample.consumer;

import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqMessage;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.core.support.mq.annotatioin.RedisMqConsumer;
import com.codeartist.component.core.util.JSON;
import com.codeartist.component.mq.sample.entity.Example;
import com.codeartist.component.mq.sample.entity.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * RedisMQ消费者
 *
 * @author AiJiangnan
 * @date 2022/11/25
 */
@Slf4j
@Component
public class RedisMqConsumers {

    @RedisMqConsumer(Topics.REDIS_TOPIC)
    public MqMessage consumer1(MqContext message) {
        String group = message.getGroup();
        String topic = message.getTopic();
        String tag = message.getTag();
        String body = message.getRecord();
        Example example = JSON.parseObject(body, Example.class);
        log.info("Consumer1 message at group:{}, topic:{}, tag:{}\n{}", group, topic, tag, example);

        example.setCount(example.getCount() + 1);

        return MqMessage.builder()
                .type(MqType.REDIS)
                .topic(Topics.REDIS_TOPIC_NEXT)
                .body(example)
                .build();
    }

    @RedisMqConsumer(Topics.REDIS_TOPIC_NEXT)
    public void consumer2(MqContext message) {
        String group = message.getGroup();
        String topic = message.getTopic();
        String tag = message.getTag();
        String body = message.getRecord();
        Example example = JSON.parseObject(body, Example.class);
        log.info("Consumer2 message at group:{}, topic:{}, tag:{}\n{}", group, topic, tag, example);
    }
}
