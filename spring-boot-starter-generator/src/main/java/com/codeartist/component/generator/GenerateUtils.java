package com.codeartist.component.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.codeartist.component.generator.entity.GenerateProperties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

import static java.lang.String.format;

/**
 * 生成工具类
 *
 * @author AiJiangnan
 * @date 2022/8/2
 */
public final class GenerateUtils {

    private GenerateUtils() {
    }

    public static void generate(GenerateProperties properties) {
        String projectPath = Optional.ofNullable(properties.getProjectPath())
                .orElse(System.getProperty("user.dir"));

        loadDatasource(properties);

        if (properties.getTablesPrefix() == null || properties.getTablesPrefix().length == 0) {
            properties.setTablesPrefix("t_");
        }

        if (ObjectUtils.isEmpty(properties.getPackageName())) {
            properties.setPackageName("com.codeartist");
        }

        FastAutoGenerator.create(new DataSourceConfig.Builder(properties.getUrl(), properties.getUsername(), properties.getPassword())
                        .dbQuery(properties.getDbQuery())
                        .databaseQueryClass(SQLQuery.class))
                .globalConfig(builder -> builder
                        .author("CodeGenerator")
                        .disableOpenDir()
                        .disableServiceInterface()
                        .dateType(DateType.TIME_PACK)
                        .outputDir(format("%s/src/main/java", projectPath))
                )
                .packageConfig(builder -> builder
                        .parent(properties.getPackageName())
                        .entity("entity")
                        .serviceImpl("service")
                        .pathInfo(Collections.singletonMap(OutputFile.xml,
                                format("%s/src/main/resources/%s/mapper", projectPath, parseDir(properties.getPackageName())))
                        )
                )
                .strategyConfig(builder -> builder
                        .addInclude(properties.getTables())
                        .addTablePrefix(properties.getTablesPrefix())
                        // Entity
                        .entityBuilder()
                        .javaTemplate("tpl/entity.java")
                        .disableSerialVersionUID()
                        .enableLombok()
                        .enableFileOverride()
                        // Controller
                        .controllerBuilder()
                        .template("tpl/controller.java")
                        .enableRestStyle()
                        // .enableFileOverride()
                        // Service
                        .serviceBuilder()
                        .serviceTemplate("tpl/serviceImpl.java")
                        .serviceImplTemplate("tpl/serviceImpl.java")
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sService")
                        // .enableFileOverride()
                        // Mapper
                        .mapperBuilder()
                        .enableBaseResultMap())
                // .enableFileOverride())
                .injectionConfig(builder -> builder
                        // vo
                        .customFile(build -> build.fileName("VO.java")
                                .templatePath("tpl/entity_vo.java.ftl")
                                .packageName("entity/vo")
                                // .enableFileOverride()
                                .build())
                        // param
                        .customFile(build -> build.fileName("Param.java")
                                .templatePath("tpl/entity_param.java.ftl")
                                .packageName("entity/param")
                                // .enableFileOverride()
                                .build())
                        // converter
                        .customFile(build -> build.fileName("Converter.java")
                                .templatePath("tpl/entity_converter.java.ftl")
                                .packageName("entity/converter")
                                // .enableFileOverride()
                                .build())
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static void loadDatasource(GenerateProperties properties) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        Properties prop = null;
        try {
            ClassPathResource resource = new ClassPathResource("application-local.yaml");
            yaml.setResources(resource);
            prop = yaml.getObject();
        } catch (IllegalStateException ignored) {
        }

        if (prop == null) {
            prop = new Properties();
        }

        properties.setUrl(prop.getProperty("spring.datasource.url", properties.getUrl()));
        properties.setUsername(prop.getProperty("spring.datasource.username", properties.getUsername()));
        properties.setPassword(prop.getProperty("spring.datasource.password", properties.getPassword()));
        properties.setPackageName(prop.getProperty("spring.application.root-package", properties.getPackageName()));
    }

    private static String parseDir(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }
}
