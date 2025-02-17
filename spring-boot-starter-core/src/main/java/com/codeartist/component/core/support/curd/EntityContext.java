package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.flow.Context;

/**
 * 实体操作上下文
 *
 * @param <P> 参数实体类型
 * @param <D> 数据库实体类型
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityContext<P, D> extends Context<P> {

    EntityAction getAction();

    D getEntity();

    D getOldEntity();
}
