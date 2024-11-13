package com.codeartist.component.mq.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedisMQ配置
 *
 * @author AiJiangnan
 * @date 2021/5/9
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mq")
public class MqProperties {

    private Redis redis = new Redis();

    @Getter
    @Setter
    public static class Redis {

        /**
         * 消息最大长度
         */
        private int queueMaxSize = 1000;
    }
}
