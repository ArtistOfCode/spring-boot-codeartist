package com.codeartist.component.core.support.business;


/**
 * 上下文操作前置处理
 *
 * @author AiJiangnan
 * @date 2024/11/21
 */
public interface PreConsumer<P, R, C extends Context<P, R>> extends BizConsumer<P, R, C> {
}
