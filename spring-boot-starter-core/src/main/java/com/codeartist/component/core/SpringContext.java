package com.codeartist.component.core;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import javax.validation.*;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Spring上下文工具类
 *
 * @author AiJiangnan
 * @date 2023/5/11
 */
public final class SpringContext implements EnvironmentAware, ApplicationContextAware {

    private static Environment environment;
    private static ApplicationContext applicationContext;
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static boolean acceptsProfiles(String... profiles) {
        return environment.acceptsProfiles(Profiles.of(profiles));
    }

    // Environment

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        return environment.getProperty(key, targetType);
    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }

    public static String getRequiredProperty(String key) throws IllegalStateException {
        return environment.getRequiredProperty(key);
    }

    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return environment.getRequiredProperty(key, targetType);
    }

    public static String resolvePlaceholders(String text) {
        return environment.resolvePlaceholders(text);
    }

    // ApplicationContext

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static Object getBean(String name, Object... args) {
        return applicationContext.getBean(name, args);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return applicationContext.getBean(requiredType, args);
    }

    public static <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
        return applicationContext.getBeanProvider(requiredType);
    }

    public static <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
        return applicationContext.getBeanProvider(requiredType);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    public static void publishEvent(Object event) {
        applicationContext.publishEvent(event);
    }

    // Validator

    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
        if (CollectionUtils.isEmpty(violations)) {
            return;
        }
        throw new ConstraintViolationException(violations);
    }

    public static <T> void validateProperty(T object, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validateProperty(object, propertyName, groups);
        if (CollectionUtils.isEmpty(violations)) {
            return;
        }
        throw new ConstraintViolationException(violations);
    }

    public static <T> void validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validateValue(beanType, propertyName, value, groups);
        if (CollectionUtils.isEmpty(violations)) {
            return;
        }
        throw new ConstraintViolationException(violations);
    }

    // Message

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return applicationContext.getMessage(code, args, defaultMessage, getDefaultLocale());
    }

    public static String getMessage(String code, Object[] args) {
        return applicationContext.getMessage(code, args, getDefaultLocale());
    }

    public static String getMessage(String code) {
        return applicationContext.getMessage(code, new Object[0], getDefaultLocale());
    }

    public static String getMessage(MessageSourceResolvable resolvable) {
        return applicationContext.getMessage(resolvable, getDefaultLocale());
    }

    private static Locale getDefaultLocale() {
        return LocaleContextHolder.getLocale();
    }

    // Transaction

    public static void beforeCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void beforeCompletion(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void beforeCompletion() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void afterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    public static void afterCompletion(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringContext.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
