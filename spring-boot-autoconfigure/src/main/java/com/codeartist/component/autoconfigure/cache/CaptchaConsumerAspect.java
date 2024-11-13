package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.core.redis.RedisCache;
import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.enums.GlobalConstants;
import com.codeartist.component.core.exception.BusinessException;
import com.codeartist.component.core.support.captcha.CaptchaParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.ObjectUtils;

@Aspect
public class CaptchaConsumerAspect {

    private static final String CAPTCHA_ERROR_COUNT_KEY = "CAPTCHA_ERROR_COUNT:";

    private final RedisCache redisCache;

    public CaptchaConsumerAspect(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Around("restController() && args(param,..)")
    public Object doAround(ProceedingJoinPoint joinPoint, CaptchaParam param) throws Throwable {
        SpringContext.validate(param);

        String key = GlobalConstants.RedisKey.CAPTCHA_KEY + param.getKey();
        String errorCountKey = CAPTCHA_ERROR_COUNT_KEY + param.getKey();

        String actualCode = redisCache.get(key, String.class);
        if (ObjectUtils.isEmpty(actualCode)) {
            throw new BusinessException(GlobalErrorCode.GLOBAL_CAPTCHA_ERROR);
        }

        Long errorCount = redisCache.get(errorCountKey, Long.class);
        if (errorCount != null && errorCount >= 3) {
            redisCache.delete(key);
            redisCache.delete(errorCountKey);
            throw new BusinessException(GlobalErrorCode.GLOBAL_CAPTCHA_EXPIRE);
        }

        if (actualCode.equalsIgnoreCase(param.getCode())) {
            Object proceed = joinPoint.proceed();
            redisCache.delete(key);
            redisCache.delete(errorCountKey);
            return proceed;
        }

        redisCache.inc(errorCountKey);
        throw new BusinessException(GlobalErrorCode.GLOBAL_CAPTCHA_ERROR);
    }
}