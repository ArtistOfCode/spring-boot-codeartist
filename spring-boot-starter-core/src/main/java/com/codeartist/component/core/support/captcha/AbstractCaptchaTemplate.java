package com.codeartist.component.core.support.captcha;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author AiJiangnan
 * @date 2024/12/31
 */
@RequiredArgsConstructor
public abstract class AbstractCaptchaTemplate implements CaptchaTemplate {

    private final CaptchaProperties captchaProperties;

    protected CaptchaProperties.CaptchaConfig getConfig(CaptchaType type) {
        switch (type) {
            case PIC:
                return this.captchaProperties.getPic();
            case SMS:
                return this.captchaProperties.getSms();
            case EMAIL:
                return this.captchaProperties.getEmail();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Getter
    @RequiredArgsConstructor
    protected static class CaptchaCache {

        private final String code;
        private byte errorCount;

        public void addErrorCount() {
            this.errorCount++;
        }
    }
}
