package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.props.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 客户端异常处理
 *
 * @author AiJiangnan
 * @date 2022/4/22
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class ClientExceptionHandler {

    @Autowired
    private AppProperties appProperties;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResp> constraintViolationException(ConstraintViolationException e) {
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name())
                .message(BadRequestException.getMessage(e))
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
}
