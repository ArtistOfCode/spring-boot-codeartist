package com.codeartist.component.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * HTTP 接口响应异常实体
 *
 * @author AiJiangnan
 * @date 2022/7/22
 */
@Getter
@Setter
@Builder
public class ErrorResp {

    /**
     * 异常来源服务
     */
    private String service;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常提示消息（国际化）
     */
    private String message;
    /**
     * 异常堆栈，返回给客户端（非生产环境测试使用）
     */
    private String stackTrace;
}
