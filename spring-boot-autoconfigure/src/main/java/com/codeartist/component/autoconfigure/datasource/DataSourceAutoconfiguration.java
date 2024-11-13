package com.codeartist.component.autoconfigure.datasource;

import com.baomidou.mybatisplus.autoconfigure.*;
import com.codeartist.component.core.entity.enums.Environments;
import com.codeartist.component.datasource.bean.MultiDataSourceProperties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 多数据自动源配置
 *
 * @author AiJiangnan
 * @date 2024/4/2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MultiDataSourceProperties.class})
@EnableConfigurationProperties(MybatisPlusProperties.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DataSourceAutoconfiguration {

    /**
     * 单元测试使用内置数据库
     */
    @Bean
    @Profile(Environments.ProfileConst.JUNIT_PROFILE)
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(StandardCharsets.UTF_8.name())
                .ignoreFailedDrops(true)
                .addScript("sql/init.sql")
                .build();
    }

    /**
     * 使用MyBatisPlus的配置来创建SqlSessionFactory和SqlSessionTemplate
     */
    @Bean
    @SuppressWarnings("rawtypes")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MybatisPlusAutoConfiguration mybatisPlusConfigurationBean(MybatisPlusProperties properties,
                                                                     ObjectProvider<Interceptor[]> interceptorsProvider,
                                                                     ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                                                     ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                                                     ResourceLoader resourceLoader,
                                                                     ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                                                     ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                                                     ObjectProvider<List<SqlSessionFactoryBeanCustomizer>> sqlSessionFactoryBeanCustomizers,
                                                                     ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
                                                                     ApplicationContext applicationContext) {
        return new MybatisPlusAutoConfiguration(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader,
                databaseIdProvider, configurationCustomizersProvider, sqlSessionFactoryBeanCustomizers,
                mybatisPlusPropertiesCustomizerProvider, applicationContext);
    }
}
