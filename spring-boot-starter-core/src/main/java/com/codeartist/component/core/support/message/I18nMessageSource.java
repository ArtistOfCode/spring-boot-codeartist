package com.codeartist.component.core.support.message;

import org.springframework.context.MessageSourceResolvable;

/**
 * 国际化文案接口
 *
 * @author AiJiangnan
 * @date 2024/6/17
 */
public interface I18nMessageSource extends MessageSourceResolvable {

    String name();

    String getCode();

    @Override
    default String[] getCodes() {
        return new String[]{getCode()};
    }
}
