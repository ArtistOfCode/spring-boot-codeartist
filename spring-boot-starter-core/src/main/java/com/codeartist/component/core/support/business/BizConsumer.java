package com.codeartist.component.core.support.business;

import org.springframework.util.StopWatch;

import java.util.function.Consumer;

/**
 * 实体上下文保存前处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
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
        stopWatch.start(getClass().getSimpleName() + " " + context.getAction().name());
        try {
            doAccept(context);
        } finally {
            stopWatch.stop();
        }
    }
}
