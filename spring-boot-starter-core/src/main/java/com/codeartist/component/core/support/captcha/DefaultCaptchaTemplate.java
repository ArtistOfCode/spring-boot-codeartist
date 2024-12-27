package com.codeartist.component.core.support.captcha;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

/**
 * 验证码默认实现
 * <p>
 * 使用本地缓存实现验证码校验
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
@Getter
@RequiredArgsConstructor
public class DefaultCaptchaTemplate implements CaptchaTemplate {

    private final CaptchaProperties captchaProperties;

    private Cache<String, CaptchaCache> picCaptchaCache;
    private Cache<String, CaptchaCache> smsCaptchaCache;
    private Cache<String, CaptchaCache> emailCaptchaCache;

    @PostConstruct
    public void init() {
        this.picCaptchaCache = this.buildCache(captchaProperties.getPic());
        this.smsCaptchaCache = this.buildCache(captchaProperties.getSms());
        this.emailCaptchaCache = this.buildCache(captchaProperties.getEmail());
    }

    @Override
    public void accept(CaptchaParam param) {
        String cacheKey = getCacheKey(param);
        Cache<String, CaptchaCache> cache = getCache(param.getType());
        cache.put(cacheKey, new CaptchaCache(param.getCode()));
    }

    @Override
    public boolean test(CaptchaParam param) {
        String cacheKey = getCacheKey(param);
        Cache<String, CaptchaCache> cache = getCache(param.getType());
        CaptchaCache actual = cache.getIfPresent(cacheKey);

        // 验证码缓存为空
        if (actual == null) {
            return false;
        }

        // 验证码错误最大次数校验
        if (actual.errorCount >= getConfig(param.getType()).getMaxErrorCount()) {
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

    private String getCacheKey(CaptchaParam param) {
        return param.getKey();
    }

    private CaptchaProperties.CaptchaConfig getConfig(CaptchaType type) {
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

    private Cache<String, CaptchaCache> getCache(CaptchaType type) {
        switch (type) {
            case PIC:
                return this.picCaptchaCache;
            case SMS:
                return this.smsCaptchaCache;
            case EMAIL:
                return this.emailCaptchaCache;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private Cache<String, CaptchaCache> buildCache(CaptchaProperties.CaptchaConfig config) {
        return Caffeine.newBuilder().expireAfterWrite(config.getTimeout()).maximumSize(config.getMaxSize()).build();
    }

    @Getter
    @RequiredArgsConstructor
    private static class CaptchaCache {

        private final String code;
        private byte errorCount;
    }
}
