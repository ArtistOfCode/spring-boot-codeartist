package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.support.props.AppProperties;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Resilience异常处理
 *
 * @author AiJiangnan
 * @date 2025/3/11
 */
@Order(1)
@RestControllerAdvice
public class ResilienceExceptionHandler {

    @Autowired
    private AppProperties appProperties;

    @ExceptionHandler({CallNotPermittedException.class, RequestNotPermitted.class})
    public ResponseEntity<ErrorResp> rateLimiterException(RuntimeException e) {
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_RATE_LIMIT.name())
                .message(SpringContext.getMessage(GlobalErrorCode.GLOBAL_RATE_LIMIT.getCode()))
                .build();

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(error);
    }
}
