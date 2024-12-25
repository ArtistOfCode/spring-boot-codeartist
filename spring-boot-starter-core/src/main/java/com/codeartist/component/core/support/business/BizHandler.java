package com.codeartist.component.core.support.business;

import java.util.function.Function;

/**
 * 业务处理器，整个生命周期接口
 *
 * @author AiJiangnan
 * @since 2022-08-31
 */
public interface BizHandler<P, R, C extends Context<P, R>> extends Function<P, R> {

    /**
     * 创建业务上下文
     */
    C createContext(P param);

    /**
     * 基础参数检查
     */
    void basicCheck(P param);

    /**
     * 业务检查
     */
    void businessCheck(C context);

    /**
     * 业务逻辑前置处理器
     */
    void preConsumer(C context);

    /**
     * 业务逻辑后置处理
     */
    void postConsumer(C context);

    /**
     * 执行业务逻辑
     */
    void execute(C context);

    /**
     * 业务逻辑事件通知
     */
    void publishEvent(C context);

    /**
     * 业务逻辑最终处理
     */
    void close(C context);

    @Override
    default R apply(P param) {
        basicCheck(param);
        C context = createContext(param);

        try {
            businessCheck(context);
            preConsumer(context);
            execute(context);
            postConsumer(context);
            publishEvent(context);
            return context.getResult();
        } finally {
            close(context);
        }
    }
}
