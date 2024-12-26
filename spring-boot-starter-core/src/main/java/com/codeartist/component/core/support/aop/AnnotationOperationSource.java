package com.codeartist.component.core.support.aop;


import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解AOP操作源缓存
 *
 * @author AiJiangnan
 * @date 2024/12/26
 */
public abstract class AnnotationOperationSource<T> {

    private static final Collection<?> NULL_CACHING_ATTRIBUTE = Collections.emptyList();

    protected final Map<MethodClassKey, Collection<T>> attributeCache = new ConcurrentHashMap<>(16);

    /**
     * 获取可操作注解
     */
    protected abstract Set<Class<? extends Annotation>> getOperationAnns();

    /**
     * 解析注解为操作实体
     */
    protected abstract Collection<T> parseAnnotations(Collection<? extends Annotation> anns);

    @SuppressWarnings("unchecked")
    public Collection<T> getOperations(Method method, Class<?> targetClass) {
        // 从缓存中获取带注解的方法
        MethodClassKey key = new MethodClassKey(method, targetClass);
        Collection<T> cached = attributeCache.get(key);

        if (cached != null) {
            return (cached != NULL_CACHING_ATTRIBUTE ? cached : null);
        }

        // 缓存中没有解析方法注解
        Collection<? extends Annotation> anns = AnnotatedElementUtils.getAllMergedAnnotations(method, getOperationAnns());
        if (anns.isEmpty()) {
            return null;
        }

        Collection<T> cacheOps = this.parseAnnotations(anns);
        if (cacheOps != null) {
            this.attributeCache.put(key, cacheOps);
        } else {
            this.attributeCache.put(key, (Collection<T>) NULL_CACHING_ATTRIBUTE);
        }
        return cacheOps;
    }
}
