package com.codeartist.component.core.support.flow;

/**
 * 业务检查，生命周期处理接口
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface BizChecker<P, C extends Context<P>> extends Handler<C> {

    void doAccept(C context);

    @Override
    default void accept(C context) {
        doAccept(context);
    }
}
