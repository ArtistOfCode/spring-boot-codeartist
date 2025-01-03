package com.codeartist.component.core.support.security;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 脱敏注解格式化
 *
 * @author AiJiangnan
 * @date 2025/1/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface SensitiveFormat {

    /**
     * 脱敏内置类型
     */
    SensitiveType type() default SensitiveType.CUSTOM;

    /**
     * 字符串前面保留位数
     */
    int prefix() default 1;

    /**
     * 字符串后面保留位数
     */
    int suffix() default 1;

    /**
     * 脱敏掩盖字符
     */
    char mask() default '*';
}
