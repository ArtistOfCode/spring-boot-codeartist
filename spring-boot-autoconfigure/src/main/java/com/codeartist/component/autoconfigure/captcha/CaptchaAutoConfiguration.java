package com.codeartist.component.autoconfigure.captcha;


import com.codeartist.component.core.support.captcha.CaptchaTemplate;
import com.codeartist.component.core.support.captcha.DefaultCaptchaTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码自动配置
 *
 * @author AiJiangnan
 * @date 2024/12/27
 */
@Configuration(proxyBeanMethods = false)
public class CaptchaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaTemplate captchaTemplate() {
        return new DefaultCaptchaTemplate();
    }
}
