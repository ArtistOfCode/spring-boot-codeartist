package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.props.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 客户端异常处理
 *
 * @author AiJiangnan
 * @date 2022/4/22
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ClientExceptionHandler {

    @Autowired
    private AppProperties appProperties;

    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResp> validateException(Exception e) {
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name())
                .message(parseExceptionMessage(e))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResp> badRequestException(BadRequestException e) {
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(e.getCode())
                .message(SpringContext.getMessage(e.getMessagesourceresolvable()))
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }

    private String parseExceptionMessage(Exception e) {
        if (e instanceof BindException) {
            List<FieldError> fieldErrors = ((BindException) e).getFieldErrors();
            if (CollectionUtils.isEmpty(fieldErrors)) {
                return null;
            }
            FieldError fieldError = fieldErrors.get(0);
            return "[" + fieldError.getField() + "]" + fieldError.getDefaultMessage();
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            if (CollectionUtils.isEmpty(violations)) {
                return null;
            }
            ConstraintViolation<?> first = violations.stream().findFirst().get();
            return "[" + first.getPropertyPath() + "]" + first.getMessage();
        }
        return null;
    }
}
