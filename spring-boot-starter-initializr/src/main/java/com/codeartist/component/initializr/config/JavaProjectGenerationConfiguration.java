package com.codeartist.component.initializr.config;

import com.codeartist.component.initializr.contributor.CustomDroneContributor;
import com.codeartist.component.initializr.contributor.CustomMavenBuild;
import com.codeartist.component.initializr.contributor.code.CustomApplicationYamlContributor;
import com.codeartist.component.initializr.contributor.code.CustomPackageInfoContributor;
import com.codeartist.component.initializr.contributor.code.CustomTestSourceContributor;
import io.spring.initializr.generator.condition.ConditionalOnLanguage;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.java.*;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.TestApplicationTypeCustomizer;
import io.spring.initializr.generator.spring.code.TestSourceCodeCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;

/**
 * Java项目生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/6
 */
@ProjectGenerationConfiguration
@ConditionalOnLanguage(JavaLanguage.ID)
public class JavaProjectGenerationConfiguration {

    private final ProjectDescription description;

    private final IndentingWriterFactory indentingWriterFactory;

    public JavaProjectGenerationConfiguration(ProjectDescription description,
                                              IndentingWriterFactory indentingWriterFactory) {
        this.description = description;
        this.indentingWriterFactory = indentingWriterFactory;
    }

    @Bean
    public TestApplicationTypeCustomizer<JavaTypeDeclaration> junitJupiterTestMethodContributor() {
        return (typeDeclaration) -> {
            typeDeclaration.extend("com.codeartist.component.core.support.test.AbstractSpringRunnerTests");
            JavaMethodDeclaration method = JavaMethodDeclaration.method("test").returning("void").body();
            method.annotate(Annotation.name("org.junit.jupiter.api.Test"));
            typeDeclaration.addMethodDeclaration(method);
        };
    }

    @Bean
    public CustomTestSourceContributor<JavaTypeDeclaration, JavaCompilationUnit, JavaSourceCode> customTestSourceContributor(
            ObjectProvider<TestApplicationTypeCustomizer<?>> testApplicationTypeCustomizers,
            ObjectProvider<TestSourceCodeCustomizer<?, ?, ?>> testSourceCodeCustomizers) {
        return new CustomTestSourceContributor<>(this.description, JavaSourceCode::new,
                new JavaSourceCodeWriter(this.indentingWriterFactory), testApplicationTypeCustomizers,
                testSourceCodeCustomizers);
    }

    @Bean
    public CustomPackageInfoContributor customPackageInfoContributor(ProjectDescription description, CustomMavenBuild build) {
        return new CustomPackageInfoContributor(description, build);
    }

    @Bean
    public CustomApplicationYamlContributor applicationYamlContributor() {
        return new CustomApplicationYamlContributor();
    }

    @Bean
    public CustomApplicationYamlContributor applicationLocalYamlContributor() {
        return new CustomApplicationYamlContributor("application-local.yaml");
    }

    @Bean
    public CustomApplicationYamlContributor bootstrapYamlContributor() {
        return new CustomApplicationYamlContributor("bootstrap.yaml");
    }

    @Bean
    public CustomDroneContributor customDroneContributor(ProjectDescription description) {
        return new CustomDroneContributor(description);
    }
}
