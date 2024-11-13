package com.codeartist.component.core.support.captcha;


import java.util.function.Consumer;

/**
 * 验证码处理
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
@FunctionalInterface
public interface CaptchaConsumer extends Consumer<CaptchaParam> {
}
