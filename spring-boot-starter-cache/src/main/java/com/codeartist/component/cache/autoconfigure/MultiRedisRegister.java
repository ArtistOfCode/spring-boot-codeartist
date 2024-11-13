package com.codeartist.component.cache.autoconfigure;

import com.codeartist.component.cache.bean.MultiRedisProperties;
import com.codeartist.component.cache.core.redis.RedisCache;
import com.codeartist.component.cache.core.redis.SpringRedisCache;
import io.lettuce.core.resource.ClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

/**
 * Redis多数据连接注册Bean
 *
 * @author AiJiangnan
 * @date 2023-11-14
 */
@Slf4j
public class MultiRedisRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {

    private static final String SPRING_REDIS_PREFIX = "spring.redis";

    private BeanFactory beanFactory;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
        BindResult<MultiRedisProperties> bindResult = Binder.get(environment).bind(SPRING_REDIS_PREFIX, MultiRedisProperties.class);

        if (!bindResult.isBound()) {
            return;
        }

        MultiRedisProperties multiProperties = bindResult.get();

        if (CollectionUtils.isEmpty(multiProperties.getMulti())) {
            return;
        }

        multiProperties.getMulti().forEach((name, properties) -> {
            registerLettuceConnectionFactory(registry, name, properties);
            registerStringRedisTemplate(registry, name);
            registerRedisCache(registry, name);
        });
    }

    private void registerLettuceConnectionFactory(BeanDefinitionRegistry registry, String name, RedisProperties properties) {
        String beanName = name + LettuceConnectionFactory.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(LettuceConnectionFactory.class, () -> newLettuceConnectionFactory(properties))
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, LettuceConnectionFactory.class);
    }

    private void registerStringRedisTemplate(BeanDefinitionRegistry registry, String name) {
        String beanName = name + StringRedisTemplate.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(StringRedisTemplate.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .addConstructorArgReference(name + LettuceConnectionFactory.class.getSimpleName())
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, StringRedisTemplate.class);
    }

    private void registerRedisCache(BeanDefinitionRegistry registry, String name) {
        String beanName = name + RedisCache.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(SpringRedisCache.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .addConstructorArgReference(name + StringRedisTemplate.class.getSimpleName())
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, RedisCache.class);
    }

    private LettuceConnectionFactory newLettuceConnectionFactory(RedisProperties properties) {
        ClientResources clientResources = beanFactory.getBean(ClientResources.class);
        return new RedisLettuceConnectionFactory(properties, clientResources)
                .buildStandaloneConnectionFactory();
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private void log(String beanName, Class<?> bean) {
        log.info("Bean '{}' of type [{}] is registered", beanName, bean.getName());
    }
}
