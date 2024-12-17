package com.codeartist.component.core.support.curd;

/**
 * 实体上下文检查抽象类
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityChecker<P, D, C extends EntityContext<P, D>> extends EntityConsumer<P, D, C> {
}
