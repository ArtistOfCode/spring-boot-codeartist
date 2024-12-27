package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import org.springframework.http.HttpStatus;

/**
 * 请求异常，返回客户端异常消息，后端不打印日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BadRequestException extends HttpException {

    public BadRequestException(I18nMessageSource i18nMessageSource) {
        this(HttpStatus.BAD_REQUEST, i18nMessageSource);
    }

    public BadRequestException(I18nMessageSource i18nMessageSource, Object[] args) {
        this(HttpStatus.BAD_REQUEST, i18nMessageSource, args);
    }

    public BadRequestException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource) {
        super(httpStatus, i18nMessageSource, null);
    }

    public BadRequestException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args) {
        super(httpStatus, i18nMessageSource, args);
    }

    public BadRequestException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args, Throwable cause) {
        super(httpStatus, i18nMessageSource, args, cause);
    }
}
