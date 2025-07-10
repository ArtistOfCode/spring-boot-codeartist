package com.codeartist.component.core.support.captcha;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码默认实现
 * <p>
 * 使用本地缓存实现验证码校验
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
@Getter
public class DefaultCaptchaTemplate extends AbstractCaptchaTemplate {

    private final static Map<CaptchaType, Cache<String, CaptchaCache>> cacheMap = new HashMap<>(CaptchaType.values().length);

    private final ObjectProvider<Caffeine<Object, Object>> caffeineBuilder;

    public DefaultCaptchaTemplate(CaptchaProperties captchaProperties, ObjectProvider<Caffeine<Object, Object>> caffeineBuilder) {
        super(captchaProperties);
        this.caffeineBuilder = caffeineBuilder;
    }

    @PostConstruct
    public void init() {
        Arrays.stream(CaptchaType.values()).forEach(type -> cacheMap.put(type, this.buildCache(getConfig(type))));
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
        if (actual.getErrorCount() >= getConfig(param.getType()).getMaxErrorCount()) {
            cache.invalidate(cacheKey);
            return false;
        }

        // 验证码校验
        if (actual.getCode().equalsIgnoreCase(param.getCode())) {
            cache.invalidate(cacheKey);
            return true;
        } else {
            actual.addErrorCount();
            cache.put(cacheKey, actual);
            return false;
        }
    }

    private String getCacheKey(CaptchaParam param) {
        return param.getKey();
    }

    private Cache<String, CaptchaCache> getCache(CaptchaType type) {
        return cacheMap.get(type);
    }

    private Cache<String, CaptchaCache> buildCache(CaptchaProperties.CaptchaConfig config) {
        return this.caffeineBuilder.getObject()
                .expireAfterWrite(config.getTimeout())
                .maximumSize(config.getMaxSize())
                .build();
    }
}
