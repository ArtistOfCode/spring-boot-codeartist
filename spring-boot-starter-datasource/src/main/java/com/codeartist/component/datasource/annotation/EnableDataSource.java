package com.codeartist.component.datasource.annotation;

import com.codeartist.component.datasource.autoconfigure.DataSourceSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 启用多数据源配置
 *
 * @author AiJiangnan
 * @date 2024/8/2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DataSourceSelector.class)
public @interface EnableDataSource {

    @AliasFor("dynamic")
    boolean value() default false;

    /**
     * 是否为动态数据源
     */
    @AliasFor("value")
    boolean dynamic() default false;
}
