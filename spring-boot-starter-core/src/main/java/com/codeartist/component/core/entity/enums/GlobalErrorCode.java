package com.codeartist.component.core.entity.enums;

import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 接口全局错误码
 *
 * @author AiJiangnan
 * @date 2020/9/11
 */
@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements I18nMessageSource {

    /**
     * 全局客户端异常
     */
    GLOBAL_CLIENT_ERROR("global.client.error"),
    /**
     * 全局业务异常
     */
    GLOBAL_BUSINESS_ERROR("global.business.error"),
    /**
     * 全局服务异常
     */
    GLOBAL_SERVICE_ERROR("global.service.error"),
    /**
     * 全局异常-业务正在处理
     */
    GLOBAL_SERVICE_BUSY_ERROR("global.service.busy.error"),
    /**
     * 全局异常-验证码已过期
     */
    GLOBAL_CAPTCHA_EXPIRE("global.captcha.expire"),
    /**
     * 全局异常-验证码错误
     */
    GLOBAL_CAPTCHA_ERROR("global.captcha.error"),
    /**
     * 全局异常-数据不存在
     */
    GLOBAL_DATA_NULL_ERROR("global.data.null.error"),

    ;

    private final String code;
}
