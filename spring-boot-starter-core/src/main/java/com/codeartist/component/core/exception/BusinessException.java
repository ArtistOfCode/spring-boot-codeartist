package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import org.springframework.http.HttpStatus;

/**
 * 业务异常，返回客户端异常消息，warn 级别日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BusinessException extends HttpException {

    public BusinessException(I18nMessageSource i18nMessageSource) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, i18nMessageSource);
    }

    public BusinessException(I18nMessageSource i18nMessageSource, Object[] args) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, i18nMessageSource, args);
    }

    public BusinessException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource) {
        super(httpStatus, i18nMessageSource, null);
    }

    public BusinessException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args) {
        super(httpStatus, i18nMessageSource, args);
    }

    public BusinessException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args, Throwable cause) {
        super(httpStatus, i18nMessageSource, args, cause);
    }
}
