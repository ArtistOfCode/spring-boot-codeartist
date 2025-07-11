package com.codeartist.component.core.support.flow;

import org.springframework.util.StopWatch;
import org.springframework.validation.Errors;

/**
 * 业务处理器上下文
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface Context<P> {

    /**
     * 计时器
     */
    StopWatch getStopWatch();

    /**
     * 操作动作类型
     */
    Enum<?> getAction();

    /**
     * 请求参数
     */
    P getParam();

    /**
     * 响应结果
     */
    Object getResult();

    /**
     * 错误信息
     */
    Errors getErrors();

    /**
     * 结束操作
     */
    void close();
}
