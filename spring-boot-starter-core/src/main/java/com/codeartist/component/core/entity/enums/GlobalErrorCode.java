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
     *
     * @see org.springframework.http.HttpStatus#BAD_REQUEST
     */
    GLOBAL_CLIENT_ERROR("global.client.error"),
    /**
     * 全局业务异常
     *
     * @see org.springframework.http.HttpStatus#INTERNAL_SERVER_ERROR
     */
    GLOBAL_BUSINESS_ERROR("global.business.error"),
    /**
     * 全局服务异常
     *
     * @see org.springframework.http.HttpStatus#SERVICE_UNAVAILABLE
     */
    GLOBAL_SERVICE_ERROR("global.service.error"),
    /**
     * 全局异常-业务正在处理
     *
     * @see org.springframework.http.HttpStatus#TOO_MANY_REQUESTS
     */
    GLOBAL_SERVICE_BUSY_ERROR("global.service.busy.error"),
    /**
     * 全局异常-没有鉴权
     *
     * @see org.springframework.http.HttpStatus#UNAUTHORIZED
     */
    GLOBAL_UNAUTHORIZED("global.service.unauthorized"),
    /**
     * 全局异常-禁止访问
     *
     * @see org.springframework.http.HttpStatus#FORBIDDEN
     */
    GLOBAL_FORBIDDEN("global.service.forbidden"),
    /**
     * 全局异常-验证码已过期
     *
     * @see org.springframework.http.HttpStatus#UNAUTHORIZED
     */
    GLOBAL_CAPTCHA_EXPIRE("global.captcha.expire"),
    /**
     * 全局异常-验证码错误
     *
     * @see org.springframework.http.HttpStatus#UNAUTHORIZED
     */
    GLOBAL_CAPTCHA_ERROR("global.captcha.error"),
    /**
     * 全局异常-数据不存在
     *
     * @see org.springframework.http.HttpStatus#SERVICE_UNAVAILABLE
     */
    GLOBAL_DATA_NULL_ERROR("global.data.null.error"),

    ;

    private final String code;
}
