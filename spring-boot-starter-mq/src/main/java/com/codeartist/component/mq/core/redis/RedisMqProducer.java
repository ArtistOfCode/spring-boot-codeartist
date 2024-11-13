package com.codeartist.component.mq.core.redis;

import com.codeartist.component.core.support.mq.MqHeaders;
import com.codeartist.component.core.support.mq.MqMessage;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.mq.core.AbstractMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisMQ生产者
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
@Slf4j
public class RedisMqProducer extends AbstractMqProducer {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisMqProducer(StringRedisTemplate stringRedisTemplate) {
        super(MqType.REDIS);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doSend(MqMessage message) {

        String topic = message.getTopic();
        MqHeaders headers = message.getHeaders();
        Object body = message.getBody();

        headers.put(MqHeaders.BODY_KEY, serialize(body));
        StringRecord record = StreamRecords.string(headers).withStreamKey(topic);
        RecordId id = stringRedisTemplate.opsForStream().add(record);

        log.info("Redis MQ send message topic:{}, recordId:{}", topic, id);
    }
}
