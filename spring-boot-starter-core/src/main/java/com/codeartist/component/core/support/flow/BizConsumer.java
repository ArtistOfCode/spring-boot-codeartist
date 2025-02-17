package com.codeartist.component.core.support.flow;

/**
 * 业务扩展处理，生命周期处理接口
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface BizConsumer<P, C extends Context<P>> extends Handler {

    default void doPreConsumer(C context) {
    }

    default void doPostConsumer(C context) {
    }

    default void preConsumer(C context) {
        doPreConsumer(context);
    }

    default void postConsumer(C context) {
        doPostConsumer(context);
    }
}
