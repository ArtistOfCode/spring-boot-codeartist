package com.codeartist.component.core.support.captcha;


/**
 * 验证码默认实现
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
public class DefaultCaptchaPredicate implements CaptchaPredicate {

    @Override
    public boolean test(CaptchaParam captchaParam) {
        throw new UnsupportedOperationException("Captcha do not have any implements.");
    }
}
