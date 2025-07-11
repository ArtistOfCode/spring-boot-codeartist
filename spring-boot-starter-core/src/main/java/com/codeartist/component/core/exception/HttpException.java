package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

/**
 * HTTP接口异常
 *
 * @author AiJiangnan
 * @date 2024/5/20
 */
@Getter
public class HttpException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final I18nMessageSource i18nMessageSource;

    private final Errors errors;

    public HttpException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Errors errors) {
        super(i18nMessageSource.name());
        this.httpStatus = httpStatus;
        this.i18nMessageSource = i18nMessageSource;
        this.errors = errors;
    }
}
