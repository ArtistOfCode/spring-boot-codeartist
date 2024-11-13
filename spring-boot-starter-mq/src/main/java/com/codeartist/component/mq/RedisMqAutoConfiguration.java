package com.codeartist.component.mq;

import com.codeartist.component.mq.core.MqConsumer;
import com.codeartist.component.mq.core.MqProducer;
import com.codeartist.component.mq.core.redis.RedisMqConsumerContainer;
import com.codeartist.component.mq.core.redis.RedisMqProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisMQ自动配置
 *
 * @author AiJiangnan
 * @date 2023/5/15
 */
@Configuration(proxyBeanMethods = false)
public class RedisMqAutoConfiguration {

    /**
     * 生产者加载，使用懒加载，优化项目启动
     */
    @Bean
    @Lazy
    public MqProducer redisMqProducer(StringRedisTemplate stringRedisTemplate) {
        return new RedisMqProducer(stringRedisTemplate);
    }

    /**
     * 消费者加载，可以考虑在本地环境中不注入，也就是在本地环境中不消费任何消息
     */
    @Bean
    public MqConsumer redisMqConsumerContainer(StringRedisTemplate stringRedisTemplate) {
        return new RedisMqConsumerContainer(stringRedisTemplate);
    }
}
