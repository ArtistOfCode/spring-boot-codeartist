package com.codeartist.component.cache.support;


import com.codeartist.component.cache.core.Cache;
import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.codeartist.component.core.support.captcha.AbstractCaptchaTemplate;
import com.codeartist.component.core.support.captcha.CaptchaParam;
import com.codeartist.component.core.support.captcha.CaptchaProperties;
import lombok.Getter;

import java.time.Duration;

/**
 * 验证码Redis实现
 * <p>
 * 使用Redis缓存实现验证码校验
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
@Getter
public class RedisCaptchaTemplate extends AbstractCaptchaTemplate {

    private final Cache cache;

    public RedisCaptchaTemplate(CaptchaProperties captchaProperties, Cache cache) {
        super(captchaProperties);
        this.cache = cache;
    }

    @Override
    public void accept(CaptchaParam param) {
        String cacheKey = getCacheKey(param);
        Duration timeout = getConfig(param.getType()).getTimeout();
        cache.set(cacheKey, new CaptchaCache(param.getCode()), timeout);
    }

    @Override
    public boolean test(CaptchaParam param) {
        String cacheKey = getCacheKey(param);
        CaptchaCache actual = cache.get(cacheKey, CaptchaCache.class);

        // 验证码缓存为空
        if (actual == null) {
            return false;
        }

        // 验证码错误最大次数校验
        Duration timeout = getConfig(param.getType()).getTimeout();
        Byte maxErrorCount = getConfig(param.getType()).getMaxErrorCount();
        if (actual.getErrorCount() >= maxErrorCount) {
            cache.delete(cacheKey);
            return false;
        }

        // 验证码校验
        if (actual.getCode().equalsIgnoreCase(param.getCode())) {
            cache.delete(cacheKey);
            return true;
        } else {
            actual.addErrorCount();
            cache.set(cacheKey, actual, timeout);
            return false;
        }
    }

    private String getCacheKey(CaptchaParam param) {
        String prefix;
        switch (param.getType()) {
            case PIC:
                prefix = GlobalConstants.PIC_CAPTCHA_KEY;
                break;
            case SMS:
                prefix = GlobalConstants.SMS_CAPTCHA_KEY;
                break;
            case EMAIL:
                prefix = GlobalConstants.EMAIL_CAPTCHA_KEY;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + param.getType());
        }
        return prefix + param.getKey();
    }
}
