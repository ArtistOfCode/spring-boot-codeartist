package com.codeartist.component.core.support.captcha;


import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * 验证码默认实现
 * <p>
 * 使用本地缓存实现验证码校验
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
@Getter
public class DefaultCaptchaTemplate implements CaptchaTemplate {

    @Value("${captcha.pic.timeout:10}")
    private Duration picCaptchaDuration;
    @Value("${captcha.sms.timeout:3}")
    private Duration smsCaptchaDuration;
    @Value("${captcha.email.timeout:5}")
    private Duration emailCaptchaDuration;
    @Value("${captcha.cache.max-size:1000}")
    private Integer maxSize;
    @Value("${captcha.cache.max-error-count:3}")
    private Byte maxErrorCount;

    private Cache<String, CaptchaCache> picCaptchaCache;
    private Cache<String, CaptchaCache> smsCaptchaCache;
    private Cache<String, CaptchaCache> emailCaptchaCache;

    @PostConstruct
    public void init() {
        this.picCaptchaCache = Caffeine.newBuilder().expireAfterWrite(this.picCaptchaDuration).maximumSize(this.maxSize).build();
        this.smsCaptchaCache = Caffeine.newBuilder().expireAfterWrite(this.smsCaptchaDuration).maximumSize(this.maxSize).build();
        this.emailCaptchaCache = Caffeine.newBuilder().expireAfterWrite(this.emailCaptchaDuration).maximumSize(this.maxSize).build();
    }

    @Override
    public void accept(CaptchaParam param) {
        String key = param.getKey();

        int idx = key.lastIndexOf(GlobalConstants.DELIMITER);
        String type = key.substring(0, idx);
        String cacheKey = key.substring(idx);

        Cache<String, CaptchaCache> cache = getCache(type);
        cache.put(cacheKey, new CaptchaCache(param.getCode()));
    }

    @Override
    public boolean test(CaptchaParam param) {
        String key = param.getKey();

        int idx = key.lastIndexOf(GlobalConstants.DELIMITER);
        String type = key.substring(0, idx);
        String cacheKey = key.substring(idx);

        Cache<String, CaptchaCache> cache = getCache(type);
        CaptchaCache actual = cache.getIfPresent(cacheKey);

        // 验证码缓存为空
        if (actual == null) {
            return false;
        }

        // 验证码错误最大次数校验
        if (actual.errorCount >= this.maxErrorCount) {
            cache.invalidate(cacheKey);
            return false;
        }

        // 验证码校验
        if (actual.code.equalsIgnoreCase(param.getCode())) {
            cache.invalidate(cacheKey);
            return true;
        } else {
            actual.errorCount++;
            cache.put(cacheKey, actual);
            return false;
        }
    }

    private Cache<String, CaptchaCache> getCache(String type) {
        switch (type) {
            case GlobalConstants.PIC_CAPTCHA_KEY:
                return this.picCaptchaCache;
            case GlobalConstants.SMS_CAPTCHA_KEY:
                return this.smsCaptchaCache;
            case GlobalConstants.EMAIL_CAPTCHA_KEY:
                return this.emailCaptchaCache;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class CaptchaCache {

        private final String code;
        private byte errorCount;
    }
}
