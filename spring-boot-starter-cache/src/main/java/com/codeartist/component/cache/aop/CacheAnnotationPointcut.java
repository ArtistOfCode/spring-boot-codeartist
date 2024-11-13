package com.codeartist.component.cache.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;

/**
 * @author J.N.AI
 * @date 2023-11-21
 */
@Getter
@RequiredArgsConstructor
public class CacheAnnotationPointcut extends StaticMethodMatcherPointcut {

    private final CacheOperationSource cacheOperationSource;

    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
        CacheOperationSource cas = getCacheOperationSource();
        return cas != null && !CollectionUtils.isEmpty(cas.getCacheOperations(method, targetClass));
    }
}
