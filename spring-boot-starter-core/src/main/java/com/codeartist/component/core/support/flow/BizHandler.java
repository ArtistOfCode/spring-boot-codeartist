package com.codeartist.component.core.support.flow;

import java.util.function.Function;

/**
 * 业务处理器，整个生命周期接口
 *
 * @param <P> 业务处理参数
 * @param <R> 业务处理返回值
 * @param <C> 业务处理上下文
 * @author AiJiangnan
 * @since 2022-08-31
 */
public interface BizHandler<P, R, C extends Context<P>> extends Function<P, R> {

    /**
     * 基础参数检查
     */
    void basicCheck(P param);

    /**
     * 创建业务上下文
     */
    C createContext(P param);

    /**
     * 业务检查
     */
    void businessCheck(C context);

    /**
     * 执行业务逻辑
     */
    void execute(C context);

    /**
     * 业务逻辑最终处理
     */
    void close(C context);
}
