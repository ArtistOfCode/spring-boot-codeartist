package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BusinessException;
import com.codeartist.component.core.exception.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 服务端异常处理
 *
 * @author AiJiangnan
 * @date 2020/9/11
 */
@Slf4j
@Order
@RestControllerAdvice
public class ServerExceptionHandler extends AbstractExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ErrorResp assertException(RuntimeException e) {
        String message = SpringContext.getMessage(e.getMessage(), null, e.getMessage());
        log.warn("Assert error occurred: {}", message, e);
        return ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_BUSINESS_ERROR.name())
                .message(message)
                .errors(Collections.emptyList())
                .fieldErrors(Collections.emptyMap())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> businessException(BusinessException e) {
        ErrorResp error = parseErrors(e.getI18nMessageSource(), e.getErrors());
        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResp> feignException(FeignException e) {
        ErrorResp error = e.getErrorResp();
        log.error("Feign exception at {}, {}:{}, {}", e.getMethodKey(), error.getCode(), error.getMessage(), error.getStackTrace(), e);
        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResp> exception(Exception e, HttpServletRequest request) {
        log.error("Server exception", e);
        String method = request.getMethod();

        String uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (uri != null) {
            metrics.counter("api_error", "method", method, "uri", uri);
        }

        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_SERVICE_ERROR.name())
                .message(SpringContext.getMessage(GlobalErrorCode.GLOBAL_SERVICE_ERROR.getCode()))
                .errors(Collections.emptyList())
                .fieldErrors(Collections.emptyMap())
                .stackTrace(trace(e, method, uri))
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    private String trace(Exception e, String method, String uri) {
        if (Environments.PROD.is()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("API: ").append(method).append(" ").append(uri).append("\n");
        printStackTrace(sb, e);
        return sb.toString();
    }

    private void printStackTrace(StringBuilder message, Throwable e) {
        message.append(e).append("\n");
        for (StackTraceElement traceElement : e.getStackTrace()) {
            message.append("\tat ").append(traceElement).append("\n");
        }
        for (Throwable se : e.getSuppressed()) {
            printStackTrace(message, se);
        }
        if (e.getCause() != null) {
            printStackTrace(message, e.getCause());
        }
    }
}
