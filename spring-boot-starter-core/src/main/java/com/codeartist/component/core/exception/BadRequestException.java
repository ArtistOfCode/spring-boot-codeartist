package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * 请求异常，返回客户端异常消息，后端不打印日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BadRequestException extends HttpException {

    public BadRequestException(I18nMessageSource i18nMessageSource) {
        this(i18nMessageSource, new BeanPropertyBindingResult(null, "badRequest"));
    }

    public BadRequestException(I18nMessageSource i18nMessageSource, Errors errors) {
        super(HttpStatus.BAD_REQUEST, i18nMessageSource, errors);
    }

    public BadRequestException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource) {
        this(httpStatus, i18nMessageSource, new BeanPropertyBindingResult(null, "badRequest"));
    }

    public BadRequestException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Errors errors) {
        super(httpStatus, i18nMessageSource, errors);
    }
}
