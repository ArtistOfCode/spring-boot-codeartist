package com.codeartist.component.core.exception;

import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.Getter;

/**
 * 国际化文案异常
 *
 * @author AiJiangnan
 * @date 2024/5/20
 */
@Getter
public class I18nMessageException extends RuntimeException {

    private final I18nMessageSource i18nMessageSource;

    public I18nMessageException(I18nMessageSource i18nMessageSource) {
        super(i18nMessageSource.name());
        this.i18nMessageSource = i18nMessageSource;
    }

    public I18nMessageException(I18nMessageSource i18nMessageSource, Throwable cause) {
        super(i18nMessageSource.name(), cause);
        this.i18nMessageSource = i18nMessageSource;
    }
}
