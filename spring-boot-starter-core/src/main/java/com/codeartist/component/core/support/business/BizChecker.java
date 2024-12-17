package com.codeartist.component.core.support.business;

/**
 * 实体上下文检查抽象类
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface BizChecker<P, R, C extends Context<P, R>> extends BizConsumer<P, R, C> {
}
