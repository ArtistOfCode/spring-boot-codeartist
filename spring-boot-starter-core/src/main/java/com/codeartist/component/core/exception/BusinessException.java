package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;

/**
 * 业务异常，返回客户端异常消息，warn 级别日志
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
public class BusinessException extends I18nMessageException {

    public BusinessException(I18nMessageSource i18nMessageSource) {
        super(i18nMessageSource);
    }

    public BusinessException(I18nMessageSource i18nMessageSource, Throwable cause) {
        super(i18nMessageSource, cause);
    }
}
