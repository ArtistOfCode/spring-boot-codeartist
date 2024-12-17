package com.codeartist.component.cache.autoconfigure;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Redis多数据源
 *
 * @author AiJiangnan
 * @date 2024/11/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({MultiRedisRegister.class})
public @interface EnableRedisDataSource {
}
