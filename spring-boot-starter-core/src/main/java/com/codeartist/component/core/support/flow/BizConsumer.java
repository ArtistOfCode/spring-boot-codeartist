package com.codeartist.component.core.support.flow;

/**
 * 业务扩展处理，生命周期处理接口
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface BizConsumer {

    interface Pre<P, C extends Context<P>> extends Handler<C> {

        void doPreConsumer(C context);

        @Override
        default void accept(C c) {
            doPreConsumer(c);
        }
    }

    interface Post<P, C extends Context<P>> extends Handler<C> {

        void doPostConsumer(C context);

        @Override
        default void accept(C c) {
            doPostConsumer(c);
        }
    }
}
