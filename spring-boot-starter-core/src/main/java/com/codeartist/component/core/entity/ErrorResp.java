package com.codeartist.component.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * HTTP 接口响应异常实体
 *
 * @author AiJiangnan
 * @date 2022/7/22
 */
@Getter
@Setter
@Builder
@Schema(description = "接口响应异常信息")
public class ErrorResp {

    @Schema(description = "异常来源服务")
    private String service;

    @Schema(description = "异常码")
    private String code;

    @Schema(description = "异常提示消息（国际化）")
    private String message;

    @Schema(description = "全局异常提示消息（国际化）")
    private List<String> errors;

    @Schema(description = "单字段异常提示消息（国际化）")
    private Map<String, String> fieldErrors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "异常堆栈，返回给客户端（非生产环境测试使用）")
    private String stackTrace;
}
