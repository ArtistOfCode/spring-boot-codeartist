package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * 业务异常，返回客户端异常消息，warn 级别日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BusinessException extends HttpException {

    public BusinessException(I18nMessageSource i18nMessageSource) {
        this(i18nMessageSource, new BeanPropertyBindingResult(null, "badRequest"));
    }

    public BusinessException(I18nMessageSource i18nMessageSource, Errors errors) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, i18nMessageSource, errors);
    }

    public BusinessException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource) {
        this(httpStatus, i18nMessageSource, new BeanPropertyBindingResult(null, "badRequest"));
    }

    public BusinessException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Errors errors) {
        super(httpStatus, i18nMessageSource, errors);
    }
}
