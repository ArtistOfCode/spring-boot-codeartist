package com.codeartist.component.core.support.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 敏感字段处理类型
 *
 * @author AiJiangnan
 * @date 2025/1/2
 */
@Getter
@RequiredArgsConstructor
public enum SensitiveType {
    CUSTOM(0, 0),
    PHONE(3, 4),
    ;

    private final int prefix;
    private final int suffix;
}
