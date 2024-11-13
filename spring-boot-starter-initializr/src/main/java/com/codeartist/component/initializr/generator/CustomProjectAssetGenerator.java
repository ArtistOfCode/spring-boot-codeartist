package com.codeartist.component.initializr.generator;

import com.codeartist.component.initializr.contributor.CustomDroneContributor;
import com.codeartist.component.initializr.contributor.CustomMavenBuild;
import com.codeartist.component.initializr.contributor.CustomMavenBuildContributor;
import com.codeartist.component.initializr.contributor.code.CustomApplicationYamlContributor;
import com.codeartist.component.initializr.contributor.code.CustomPackageInfoContributor;
import com.codeartist.component.initializr.contributor.code.CustomTestSourceContributor;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.spring.code.MainSourceCodeProjectContributor;
import io.spring.initializr.generator.spring.scm.git.GitIgnoreContributor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author AiJiangnan
 * @date 2023/6/5
 */
public class CustomProjectAssetGenerator implements ProjectAssetGenerator<Path> {

    @Override
    public Path generate(ProjectGenerationContext context) throws IOException {
        CustomMavenBuild build = context.getBean(CustomMavenBuild.class);
        ProjectDescription description = context.getBean(ProjectDescription.class);

        Path rootPath = resolveProjectDirectoryFactory(context).createProjectDirectory(description);
        Path rootDir = initializerProjectDirectory(rootPath, description);
        Path webDir = rootDir.resolve(build.webArtifactId());

        context.getBean(CustomMavenBuildContributor.class).contribute(rootDir);
        context.getBean(MainSourceCodeProjectContributor.class).contribute(webDir);
        context.getBean(CustomTestSourceContributor.class).contribute(webDir);
        context.getBean(CustomPackageInfoContributor.class).contribute(rootDir);
        context.getBeanProvider(CustomApplicationYamlContributor.class).stream()
                .forEach(contributor -> contributor.contribute(webDir));
        context.getBean(CustomDroneContributor.class).contribute(rootDir);

        context.getBean(GitIgnoreContributor.class).contribute(rootDir);

        return rootPath;
    }

    private ProjectDirectoryFactory resolveProjectDirectoryFactory(ProjectGenerationContext context) {
        return context.getBean(ProjectDirectoryFactory.class);
    }


    private Path initializerProjectDirectory(Path rootDir, ProjectDescription description) throws IOException {
        Path projectDirectory = resolveProjectDirectory(rootDir, description);
        Files.createDirectories(projectDirectory);
        return projectDirectory;
    }

    private Path resolveProjectDirectory(Path rootDir, ProjectDescription description) {
        if (description.getBaseDirectory() != null) {
            return rootDir.resolve(description.getBaseDirectory());
        }
        return rootDir;
    }
}
