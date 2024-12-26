package com.codeartist.component.core.support.mq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * MQ生产者实体
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
@Getter
@Setter
@Builder
public class MqMessage {

    /**
     * MQ类型
     */
    private MqType type;
    /**
     * MQ请求头
     */
    private MqHeaders headers;
    /**
     * 生产者主题
     */
    private String topic;
    /**
     * 生产者标签
     */
    private String tag;
    /**
     * 消息体，默认JSON序列化
     */
    private Object body;
}
