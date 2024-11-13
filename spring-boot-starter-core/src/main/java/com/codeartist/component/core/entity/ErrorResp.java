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

    private String service;
    private String code;
    private String message;
    private String stackTrace;
}
