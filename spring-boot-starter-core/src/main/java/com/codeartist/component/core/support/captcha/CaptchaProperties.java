package com.codeartist.component.core.support.captcha;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 验证码配置类
 *
 * @author AiJiangnan
 * @date 2024/12/27
 */
@Getter
@Setter
@ConfigurationProperties("spring.captcha")
public class CaptchaProperties {

    /**
     * 图形验证码配置，默认超时时间：10分钟
     */
    private CaptchaConfig pic = new CaptchaConfig() {{
        setTimeout(Duration.ofMinutes(10));
    }};
    /**
     * 短信验证码配置，默认超时时间：3分钟
     */
    private CaptchaConfig sms = new CaptchaConfig() {{
        setTimeout(Duration.ofMinutes(3));
    }};
    /**
     * 邮件验证码配置，默认超时时间：30分钟
     */
    private CaptchaConfig email = new CaptchaConfig() {{
        setTimeout(Duration.ofMinutes(30));
    }};

    @Getter
    @Setter
    public static class CaptchaConfig {
        /**
         * 验证码超时时间
         */
        private Duration timeout;
        /**
         * 最大保存Key数量（只用于本地缓存实现）
         */
        private Integer maxSize = 1000;
        /**
         * 最大校验错误次数
         */
        private Byte maxErrorCount = 3;
    }
}
