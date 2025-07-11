package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户端异常处理
 *
 * @author AiJiangnan
 * @date 2022/4/22
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ClientExceptionHandler extends AbstractExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResp bindException(BindException e) {
        return parseErrors(GlobalErrorCode.GLOBAL_CLIENT_ERROR, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResp validateException(ConstraintViolationException e) {
        String message = null;

        Map<String, String> fieldErrors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(f -> f.getPropertyPath().toString(), ConstraintViolation::getMessage));
        for (String key : fieldErrors.keySet()) {
            message = key + ":" + SpringContext.getMessage(fieldErrors.get(key));
            break;
        }

        return ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name())
                .message(message)
                .errors(Collections.emptyList())
                .fieldErrors(fieldErrors)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResp> badRequestException(BadRequestException e) {
        ErrorResp error = parseErrors(e.getI18nMessageSource(), e.getErrors());
        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }
}
