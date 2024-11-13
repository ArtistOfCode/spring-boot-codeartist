package com.codeartist.component.core.annotation;

import com.codeartist.component.core.entity.enums.Environments;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * 非生产环境Profile
 *
 * @author AiJiangnan
 * @date 2021/11/18
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile(Environments.ProfileConst.NOT_PROD_PROFILE)
public @interface Development {
}
