package com.codeartist.component.core.exception;

import com.codeartist.component.core.entity.ErrorResp;
import lombok.Getter;

/**
 * Feign调用异常，返回客户端异常消息，error 级别日志
 *
 * @author AiJiangnan
 * @date 2022/7/27
 */
@Getter
public class FeignException extends RuntimeException {

    private final String methodKey;
    private final ErrorResp errorResp;

    public FeignException(String methodKey, ErrorResp errorResp) {
        super(errorResp.getCode());
        this.methodKey = methodKey;
        this.errorResp = errorResp;
    }
}
