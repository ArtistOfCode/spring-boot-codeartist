package com.codeartist.component.core.support.cache.annotation;

import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.codeartist.component.core.support.cache.CacheType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 清除缓存注解
 *
 * @author AiJiangnan
 * @see Cache
 * @since 2018-11-07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDelete {

    @AliasFor("key")
    String value() default "";

    /**
     * 缓存的Key（支持SpEL表达式）
     */
    @AliasFor("value")
    String key() default "";

    /**
     * 缓存类型
     */
    CacheType type() default CacheType.REDIS;

    /**
     * 本地缓存指定Bean，当有多个本地缓存配置时使用
     */
    String cacheRef() default GlobalConstants.DEFAULT;

    /**
     * Redis缓存指定Bean，当有多个Redis集群时使用
     */
    String redisCacheRef() default GlobalConstants.DEFAULT;
}
