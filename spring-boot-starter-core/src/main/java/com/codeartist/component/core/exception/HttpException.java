package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.Getter;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;

/**
 * HTTP接口异常
 *
 * @author AiJiangnan
 * @date 2024/5/20
 */
@Getter
public class HttpException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String code;

    private final MessageSourceResolvable messagesourceresolvable;

    public HttpException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args) {
        super(i18nMessageSource.name());
        this.httpStatus = httpStatus;
        this.code = i18nMessageSource.name();
        this.messagesourceresolvable = buildMessageSourceResolvable(i18nMessageSource, args);
    }

    public HttpException(HttpStatus httpStatus, I18nMessageSource i18nMessageSource, Object[] args, Throwable cause) {
        super(i18nMessageSource.name(), cause);
        this.httpStatus = httpStatus;
        this.code = i18nMessageSource.name();
        this.messagesourceresolvable = buildMessageSourceResolvable(i18nMessageSource, args);
    }

    private MessageSourceResolvable buildMessageSourceResolvable(I18nMessageSource i18nMessageSource, Object[] args) {
        return new DefaultMessageSourceResolvable(i18nMessageSource.getCodes(), args, i18nMessageSource.getCodes()[0]);
    }
}
