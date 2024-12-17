package com.codeartist.component.core.support.business;

import org.springframework.util.StopWatch;

/**
 * 业务操作上下文
 *
 * @param <P> 实体类型
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface Context<P, R> {

    StopWatch getStopWatch();

    Enum<?> getAction();

    P getParam();

    R getResult();

    void clear();
}
