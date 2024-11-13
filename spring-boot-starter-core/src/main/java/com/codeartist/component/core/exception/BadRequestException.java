package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;

/**
 * 请求异常，返回客户端异常消息，后端不打印日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BadRequestException extends I18nMessageException {

    public BadRequestException(I18nMessageSource i18nMessageSource) {
        super(i18nMessageSource);
    }

    public BadRequestException(I18nMessageSource i18nMessageSource, Throwable cause) {
        super(i18nMessageSource, cause);
    }
}