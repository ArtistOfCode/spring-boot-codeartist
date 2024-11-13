package com.codeartist.component.datasource.autoconfigure;

import com.codeartist.component.datasource.bean.DynamicDatasource;
import com.codeartist.component.datasource.bean.MultiDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源注册Bean
 *
 * @author AiJiangnan
 * @date 2023-11-14
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String SPRING_DATASOURCE_PREFIX = "spring.datasource";

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
            log.warn("Dynamic datasource config is empty.");
            return;
        }

        DynamicDatasource dynamicDatasource = new DynamicDatasource();

        Map<String, DataSource> targetDataSource = new HashMap<>(multiProperties.getMulti().size());
        multiProperties.getMulti().forEach((name, properties) ->
                targetDataSource.put(name, this.newDataSource(name, properties)));

        dynamicDatasource.setTargetDataSources(Collections.unmodifiableMap(targetDataSource));
        dynamicDatasource.setDefaultTargetDataSource(targetDataSource.get(multiProperties.getDefaultName()));

        String beanName = "dataSource";
        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(DynamicDatasource.class)
                .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                .getRawBeanDefinition();
        registry.registerBeanDefinition(beanName, definition);
        log(beanName);
    }

    private DataSource newDataSource(String name, DataSourceProperties properties) {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        dataSource.setPoolName(name);
        return dataSource;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    private void log(String beanName) {
        log.info("Bean '{}' of type [{}] is registered", beanName, DynamicDatasource.class.getName());
    }
}
