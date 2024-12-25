package com.codeartist.component.core.support.business;

import org.springframework.util.StopWatch;

import java.util.function.Consumer;

/**
 * 业务处理器，生命周期处理接口
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
@FunctionalInterface
public interface BizConsumer<P, R, C extends Context<P, R>> extends Consumer<C> {

    /**
     * 获取可操作类型
     */
    default Enum<?>[] getAction() {
        return new BizAction[]{BizAction.DEFAULT};
    }

    /**
     * 业务操作
     */
    void doAccept(C context);

    @Override
    default void accept(C context) {
        StopWatch stopWatch = context.getStopWatch();
        stopWatch.start(getClass().getSimpleName());
        try {
            doAccept(context);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * 业务检查接口
     */
    interface BizChecker<P, R, C extends Context<P, R>> extends BizConsumer<P, R, C> {
    }

    /**
     * 前置处理接口
     */
    interface PreConsumer<P, R, C extends Context<P, R>> extends BizConsumer<P, R, C> {
    }

    /**
     * 后置处理接口
     */
    interface PostConsumer<P, R, C extends Context<P, R>> extends BizConsumer<P, R, C> {
    }
}
