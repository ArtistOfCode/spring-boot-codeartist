package com.codeartist.component.core.support.captcha;


/**
 * 验证码默认实现
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
public class DefaultCaptchaConsumer implements CaptchaConsumer {

    @Override
    public void accept(CaptchaParam captchaParam) {
        throw new UnsupportedOperationException("Captcha do not have a consumer");
    }
}
