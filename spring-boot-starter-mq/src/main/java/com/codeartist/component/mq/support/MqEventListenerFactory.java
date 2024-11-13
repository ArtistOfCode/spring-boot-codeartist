package com.codeartist.component.mq.support;

import com.codeartist.component.core.support.mq.MqContext;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.core.support.mq.annotatioin.MqConsumerListener;
import com.codeartist.component.core.support.props.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ事件监听工厂
 *
 * @author AiJiangnan
 * @date 2021/7/20
 */
public class MqEventListenerFactory implements EventListenerFactory, Ordered {

    private final Map<MqType, Set<MqContext>> listenerMap = new ConcurrentHashMap<>();

    @Autowired
    private AppProperties appProperties;

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }

    @Override
    public boolean supportsMethod(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, MqConsumerListener.class);
    }

    @Override
    public ApplicationListener<?> createApplicationListener(String beanName, Class<?> type, Method method) {
        MqConsumerListener ann = AnnotatedElementUtils.findMergedAnnotation(method, MqConsumerListener.class);
        if (ann == null) {
            throw new IllegalStateException("No MqConsumerListener ann found on method: " + method);
        }
        MqContext annotation = parseMessage(ann);
        return new MqConsumerListenerMethodAdapter(beanName, type, method, annotation);
    }

    public Set<MqContext> getListeners(MqType type) {
        return this.listenerMap.get(type);
    }

    private MqContext parseMessage(MqConsumerListener ann) {

        MqType type = ann.type();
        MqContext annotation = MqContext.builder()
                .type(type)
                .group(getGroup())
                .topic(ann.topic())
                .tag(ann.tag())
                .build();

        listenerMap.computeIfAbsent(type, k -> new HashSet<>());
        listenerMap.get(type).add(annotation);
        return annotation;
    }

    private String getGroup() {
        return appProperties.getName() + "-group";
    }
}
