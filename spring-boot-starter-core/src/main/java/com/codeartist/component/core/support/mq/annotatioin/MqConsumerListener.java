package com.codeartist.component.core.support.mq.annotatioin;

import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqHeaders;
import com.codeartist.component.core.support.mq.MqType;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * MQ消费者监听器注解
 *
 * @author AiJiangnan
 * @date 2021/5/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventListener(classes = MqContext.class)
public @interface MqConsumerListener {

    @AliasFor("topic")
    String value() default "";

    MqType type();

    @AliasFor("value")
    String topic() default "";

    String tag() default MqHeaders.DEFAULT_TAG;
}
