package com.codeartist.component.mq.core.redis;

import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqHeaders;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.mq.core.AbstractMqConsumer;
import com.codeartist.component.mq.exception.MqException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * RedisMQ消费者
 *
 * @author AiJiangnan
 * @date 2023/5/12
 */
@Slf4j
public class RedisMqConsumerContainer extends AbstractMqConsumer {

    private final StringRedisTemplate stringRedisTemplate;
    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> container;

    public RedisMqConsumerContainer(StringRedisTemplate stringRedisTemplate) {
        super(MqType.REDIS);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void init() {
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainerOptions.builder()
                        .batchSize(10)
                        .executor(mqConsumerExecutor)
                        .pollTimeout(Duration.ofSeconds(1))
                        .errorHandler(new RedisStreamErrorHandler())
                        .build();

        RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
        if (connectionFactory == null) {
            throw new IllegalStateException("Redis connection factory is null.");
        }
        this.container = StreamMessageListenerContainer.create(connectionFactory, options);
    }

    @Override
    protected void doRegister(MqContext listener) {
        RedisMqListener redisMqListener = new RedisMqListener(listener);

        String group = listener.getGroup();
        String topic = listener.getTopic();

        createGroupTopic(group, topic);
        consumerPending(group, topic, redisMqListener);
        registerListener(group, topic, redisMqListener);
    }

    @Override
    protected void doStart() {
        this.container.start();
    }

    @Override
    protected void doStop() {
        this.container.stop();
    }

    private void createGroupTopic(String group, String topic) {
        Boolean exists = stringRedisTemplate.hasKey(topic);
        if (Objects.equals(Boolean.FALSE, exists)) {
            StreamOperations<String, String, String> stream = stringRedisTemplate.opsForStream();
            RecordId id = stream.add(topic, Collections.singletonMap("", ""));
            stream.delete(topic, id);
            stream.trim(topic, properties.getRedis().getQueueMaxSize());
            stream.createGroup(topic, ReadOffset.latest(), group);
        }
    }

    private void registerListener(String group, String topic, RedisMqListener redisMqListener) {
        Consumer consumer = Consumer.from(group, getApplicationName());
        StreamOffset<String> offset = StreamOffset.create(topic, ReadOffset.lastConsumed());
        this.container.receiveAutoAck(consumer, offset, redisMqListener);
        log.info("Redis container register at group:{}, topic:{}", group, topic);
    }

    private void consumerPending(String group, String topic, RedisMqListener redisMqListener) {
        PendingMessagesSummary pending = stringRedisTemplate.opsForStream().pending(topic, group);
        if (pending == null) {
            return;
        }
        long total = pending.getTotalPendingMessages();
        RedisZSetCommands.Limit limit = RedisZSetCommands.Limit.limit().count((int) total);

        List<MapRecord<String, String, String>> records = stringRedisTemplate.<String, String>opsForStream()
                .range(topic, pending.getIdRange(), limit);

        if (!CollectionUtils.isEmpty(records)) {
            log.info("Redis pending message total: {}", total);
            mqConsumerExecutor.execute(() -> records.forEach(redisMqListener::onMessage));
        }
    }


    @RequiredArgsConstructor
    private class RedisMqListener implements StreamListener<String, MapRecord<String, String, String>> {

        private final MqContext listener;

        @Override
        public void onMessage(MapRecord<String, String, String> message) {
            RecordId id = message.getId();
            Map<String, String> value = message.getValue();

            String data = value.remove(MqHeaders.BODY_KEY);
            MqHeaders headers = new MqHeaders();
            headers.setId(id.getValue());
            if (!CollectionUtils.isEmpty(value)) {
                headers.putAll(value);
            }

            MqContext mqContext = MqContext.builder()
                    .type(listener.getType())
                    .headers(headers)
                    .group(listener.getGroup())
                    .topic(message.getStream())
                    .tag(MqHeaders.DEFAULT_TAG)
                    .record(data)
                    .build();

            doPublish(mqContext);

            stringRedisTemplate.opsForStream().acknowledge(listener.getTopic(), listener.getGroup(), id);
        }
    }

    private static class RedisStreamErrorHandler implements ErrorHandler {

        @Override
        public void handleError(Throwable t) {
            try {
                throw t;
            } catch (RedisSystemException ignored) {
            } catch (Throwable e) {
                throw new MqException(e);
            }
        }
    }
}
