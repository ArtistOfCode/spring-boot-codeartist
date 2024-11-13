package com.codeartist.component.mq.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * MQ生命周期
 *
 * @author AiJiangnan
 * @date 2021/5/18
 */
public interface MqLifecycle extends InitializingBean, DisposableBean {

    void init();

    void start();

    void stop();

    @Override
    default void afterPropertiesSet() {
        init();
    }

    @Override
    default void destroy() {
        stop();
    }
}
