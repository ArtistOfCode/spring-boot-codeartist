package com.codeartist.component.core.support.curd;


/**
 * 上下文操作前置处理
 *
 * @author AiJiangnan
 * @date 2024/11/21
 */
@FunctionalInterface
public interface PostEntityConsumer<P, D, C extends EntityContext<P, D>> extends EntityConsumer<P, D, C> {
}
