package com.codeartist.component.core.support.props;


import lombok.Getter;
import lombok.Setter;

/**
 * 配置模型接口
 * <p>
 * Value 为空时会通过Key刷新
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
public interface LocalConfig {

    String getKey();

    default String getValue() {
        return null;
    }

    @Getter
    @Setter
    class Default implements LocalConfig {
        private String key;
        private String value;
    }
}
