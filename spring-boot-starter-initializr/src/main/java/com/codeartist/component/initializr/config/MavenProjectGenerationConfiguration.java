package com.codeartist.component.initializr.config;

import com.codeartist.component.initializr.contributor.CustomMavenBuild;
import com.codeartist.component.initializr.contributor.CustomMavenBuildContributor;
import com.codeartist.component.initializr.customizer.MavenBuildCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 自定义脚手架生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
public class MavenProjectGenerationConfiguration {

    @Bean
    public MavenBuildCustomizer mavenBuildCustomizer(ProjectDescription description, InitializrMetadata metadata) {
        return new MavenBuildCustomizer(description, metadata);
    }

    @Bean
    @Primary
    public CustomMavenBuild customMavenBuild(MavenBuildCustomizer mavenBuildCustomizer) {
        CustomMavenBuild build = new CustomMavenBuild();
        mavenBuildCustomizer.customize(build);
        return build;
    }

    @Bean
    public CustomMavenBuildContributor customMavenBuildContributor(CustomMavenBuild build,
                                                                   IndentingWriterFactory indentingWriterFactory) {
        return new CustomMavenBuildContributor(build, indentingWriterFactory);
    }
}
