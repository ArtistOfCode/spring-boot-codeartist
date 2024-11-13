package com.codeartist.component.core.support.curd;

import org.springframework.util.StopWatch;

/**
 * 实体操作上下文
 *
 * @param <P> 参数实体类型
 * @param <D> 数据库实体类型
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityContext<P, D> {

    EntityAction getAction();

    P getParam();

    D getEntity();

    D getOldEntity();

    StopWatch getStopWatch();

    void clear();
}
