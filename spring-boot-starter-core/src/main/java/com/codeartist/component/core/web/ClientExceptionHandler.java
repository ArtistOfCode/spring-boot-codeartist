package com.codeartist.component.core.web;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.enums.ApiHttpStatus;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.props.AppProperties;
import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

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
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        if (CollectionUtils.isEmpty(violations)) {
            log.error("ConstraintViolation is empty.", e);
            return ResponseEntity.status(ApiHttpStatus.CLIENT_WARNING.getValue()).build();
        }

        ConstraintViolation<?> first = violations.stream().findFirst().get();
        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name())
                .message(first.getPropertyPath() + first.getMessage())
                .build();

        return ResponseEntity
                .status(ApiHttpStatus.CLIENT_WARNING.getValue())
                .body(error);
    }

//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<ErrorResp> bindException(BindException e) {
//        return badRequestException(new BadRequestException(e));
//    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResp> badRequestException(BadRequestException e) {
        I18nMessageSource first = e.getI18nMessageSource();

        ErrorResp error = ErrorResp.builder()
                .service(appProperties.getName())
                .code(first.name())
                .message(SpringContext.getMessage(first))
                .build();

        return ResponseEntity
                .status(ApiHttpStatus.CLIENT_WARNING.getValue())
                .body(error);
    }

}
