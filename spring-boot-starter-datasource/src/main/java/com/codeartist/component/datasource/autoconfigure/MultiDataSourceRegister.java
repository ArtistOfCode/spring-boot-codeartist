package com.codeartist.component.datasource.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.codeartist.component.datasource.bean.MultiDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;

/**
 * 多数据源注册Bean
 *
 * @author AiJiangnan
 * @date 2023-11-14
 */
@Slf4j
public class MultiDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {

    private static final String SPRING_DATASOURCE_PREFIX = "spring.datasource";

    private BeanFactory beanFactory;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {

        BindResult<MultiDataSourceProperties> bindResult = Binder.get(environment)
                .bind(SPRING_DATASOURCE_PREFIX, MultiDataSourceProperties.class);

        if (!bindResult.isBound()) {
            return;
        }

        MultiDataSourceProperties multiProperties = bindResult.get();

        if (CollectionUtils.isEmpty(multiProperties.getMulti())) {
            log.warn("Multi datasource config is empty.");
            return;
        }

        multiProperties.getMulti().forEach((name, properties) -> {
            registerDataSource(registry, name, properties);
            registerSqlSessionFactory(registry, name);
            registerSqlSessionTemplate(registry, name);
            registerDataSourceTransactionManager(registry, name);
            registerTransactionTemplate(registry, name);
        });
    }

    public void registerDataSource(BeanDefinitionRegistry registry, String name, DataSourceProperties properties) {
        String beanName = name + DataSource.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(DataSource.class, () -> newDataSource(name, properties))
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, DataSource.class);
    }

    public void registerSqlSessionFactory(BeanDefinitionRegistry registry, String name) {
        String beanName = name + SqlSessionFactory.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(SqlSessionFactory.class, () -> newSqlSessionFactory(name))
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, SqlSessionFactory.class);
    }

    private void registerSqlSessionTemplate(BeanDefinitionRegistry registry, String name) {
        String beanName = name + SqlSessionTemplate.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(SqlSessionTemplate.class, () -> newSqlSessionTemplate(name))
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, SqlSessionTemplate.class);
    }

    private void registerDataSourceTransactionManager(BeanDefinitionRegistry registry, String name) {
        String beanName = name + DataSourceTransactionManager.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(DataSourceTransactionManager.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .addConstructorArgReference(name + DataSource.class.getSimpleName())
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, DataSourceTransactionManager.class);
    }

    private void registerTransactionTemplate(BeanDefinitionRegistry registry, String name) {
        String beanName = name + TransactionTemplate.class.getSimpleName();

        BeanDefinition definition = BeanDefinitionBuilder
                .genericBeanDefinition(TransactionTemplate.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .addConstructorArgReference(name + DataSourceTransactionManager.class.getSimpleName())
                .getRawBeanDefinition();

        registry.registerBeanDefinition(beanName, definition);
        log(beanName, TransactionTemplate.class);
    }

    private DataSource newDataSource(String name, DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        dataSource.setPoolName(name);
        return dataSource;
    }

    private SqlSessionFactory newSqlSessionFactory(String name) {
        MybatisPlusAutoConfiguration configurationBean = beanFactory.getBean(MybatisPlusAutoConfiguration.class);
        DataSource dataSource = beanFactory.getBean(name + DataSource.class.getSimpleName(), DataSource.class);
        try {
            return configurationBean.sqlSessionFactory(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SqlSessionTemplate newSqlSessionTemplate(String name) {
        MybatisPlusAutoConfiguration configurationBean = beanFactory.getBean(MybatisPlusAutoConfiguration.class);
        SqlSessionFactory sqlSessionFactory = beanFactory
                .getBean(name + SqlSessionFactory.class.getSimpleName(), SqlSessionFactory.class);
        return configurationBean.sqlSessionTemplate(sqlSessionFactory);
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
