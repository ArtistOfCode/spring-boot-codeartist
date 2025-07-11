package com.codeartist.component.core.exception;

import com.codeartist.component.core.entity.ErrorResp;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Feign调用异常，返回客户端异常消息，error 级别日志
 *
 * @author AiJiangnan
 * @date 2022/7/27
 */
@Getter
public class FeignException extends RuntimeException {

    private final String methodKey;
    private final HttpStatus httpStatus;
    private final ErrorResp errorResp;

    public FeignException(String methodKey, int status, ErrorResp errorResp) {
        this.methodKey = methodKey;
        this.httpStatus = HttpStatus.valueOf(status);
        this.errorResp = errorResp;
    }
}
