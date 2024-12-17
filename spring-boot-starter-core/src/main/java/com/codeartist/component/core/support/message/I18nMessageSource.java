package com.codeartist.component.core.support.message;

/**
 * 国际化文案接口
 *
 * @author AiJiangnan
 * @date 2024/6/17
 */
public interface I18nMessageSource {

    String name();

    String getCode();

    default String[] getCodes() {
        return new String[]{this.getCode()};
    }
}
