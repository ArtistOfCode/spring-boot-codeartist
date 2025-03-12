package com.codeartist.component.test.mock;


import com.codeartist.component.core.entity.enums.Environments.Profiles;
import com.codeartist.component.core.support.captcha.CaptchaTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

/**
 * Mock配置
 *
 * @author AiJiangnan
 * @date 2025/3/12
 */
@Profile(Profiles.JUNIT)
@Configuration(proxyBeanMethods = false)
@Import(MockAutoConfiguration.EmailMockAutoConfiguration.class)
public class MockAutoConfiguration {

    @Bean
    public CaptchaTemplate captchaTemplate() {
        return new MockCaptchaTemplate();
    }

    @ConditionalOnClass({MimeMessage.class, MimeType.class, MailSender.class})
    @EnableConfigurationProperties(MailProperties.class)
    public static class EmailMockAutoConfiguration {

        @Bean
        public JavaMailSender javaMailSender() {
            return new MockJavaMailSender();
        }
    }
}
