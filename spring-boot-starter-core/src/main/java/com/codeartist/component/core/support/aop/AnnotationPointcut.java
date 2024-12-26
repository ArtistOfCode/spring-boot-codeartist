package com.codeartist.component.core.support.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;

/**
 * 注解切点
 *
 * @author AiJiangnan
 * @date 2023-11-21
 */
@Getter
@RequiredArgsConstructor
public class AnnotationPointcut extends StaticMethodMatcherPointcut {

    private final AnnotationOperationSource<?> cacheOperationSource;

    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
        AnnotationOperationSource<?> cas = getCacheOperationSource();
        return cas != null && !CollectionUtils.isEmpty(cas.getOperations(method, targetClass));
    }
}
