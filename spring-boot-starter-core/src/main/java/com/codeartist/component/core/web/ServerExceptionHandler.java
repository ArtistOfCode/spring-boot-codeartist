package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.enums.ApiHttpStatus;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.core.exception.BusinessException;
import com.codeartist.component.core.exception.FeignException;
import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.core.support.props.AppProperties;
import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务端异常处理
 *
 * @author AiJiangnan
 * @date 2020/9/11
 */
@Slf4j
@Order(2)
@RestControllerAdvice
public class ServerExceptionHandler {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private Metrics metrics;

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResp> assertException(RuntimeException e) {
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_BUSINESS_ERROR.name())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(ApiHttpStatus.BUSINESS_WARNING.getValue())
                .body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> businessException(BusinessException e) {
        I18nMessageSource first = e.getI18nMessageSource();
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(first.name())
                .message(SpringContext.getMessage(first))
                .build();

        return ResponseEntity
                .status(ApiHttpStatus.BUSINESS_WARNING.getValue())
                .body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResp> feignException(FeignException e) {
        ErrorResp error = e.getErrorResp();
        log.error("Feign exception at {}, {}:{}, {}", e.getMethodKey(), error.getCode(), error.getMessage(), error.getStackTrace(), e);
        return ResponseEntity
                .status(ApiHttpStatus.SERVER_ERROR.getValue())
                .body(error);
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
                .message(SpringContext.getMessage(GlobalErrorCode.GLOBAL_SERVICE_ERROR))
                .stackTrace(trace(e, method, uri))
                .build();

        return ResponseEntity
                .status(ApiHttpStatus.SERVER_ERROR.getValue())
                .body(error);
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
