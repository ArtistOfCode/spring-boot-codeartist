package com.codeartist.component.mq.exception;

import com.codeartist.component.core.support.mq.MqContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * MQ异常
 *
 * @author AiJiangnan
 * @date 2021/7/23
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MqException extends RuntimeException {

    private MqContext mqContext;

    public MqException() {
        super();
    }

    public MqException(MqContext mqContext, String message) {
        super(message);
        this.mqContext = mqContext;
    }

    public MqException(MqContext mqContext, String message, Throwable cause) {
        super(message, cause);
        this.mqContext = mqContext;
    }

    public MqException(Throwable cause) {
        super(cause);
    }

    protected MqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
