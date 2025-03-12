package com.codeartist.component.test.mock;


import com.codeartist.component.core.support.captcha.CaptchaParam;
import com.codeartist.component.core.support.captcha.CaptchaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock验证码处理
 *
 * @author AiJiangnan
 * @date 2025/3/12
 */
public class MockCaptchaTemplate implements CaptchaTemplate {

    private static final Logger log = LoggerFactory.getLogger(MockCaptchaTemplate.class);

    @Override
    public void accept(CaptchaParam param) {
    }

    @Override
    public boolean test(CaptchaParam param) {
        log.info("Captcha mock type:{}, key:{}, code:{}", param.getType(), param.getKey(), param.getCode());
        return true;
    }
}
