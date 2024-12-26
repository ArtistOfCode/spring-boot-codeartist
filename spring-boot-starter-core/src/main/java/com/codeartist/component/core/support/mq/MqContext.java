package com.codeartist.component.core.support.mq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * MQ消费者实体
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
@Getter
@Setter
@Builder
public class MqContext {

    /**
     * MQ类型
     */
    private MqType type;
    /**
     * MQ请求头
     */
    private MqHeaders headers;
    /**
     * 消费者组
     */
    private String group;
    /**
     * 消费者主题
     */
    private String topic;
    /**
     * 消费者标签
     */
    private String tag;
    /**
     * 消息记录，UTF-8序列化字符串
     */
    private String record;
}
