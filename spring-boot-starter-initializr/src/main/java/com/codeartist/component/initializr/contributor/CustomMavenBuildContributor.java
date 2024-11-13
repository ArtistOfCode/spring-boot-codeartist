package com.codeartist.component.initializr.contributor;

import io.spring.initializr.generator.buildsystem.BuildWriter;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Maven项目生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/5
 */
public class CustomMavenBuildContributor implements BuildWriter, ProjectContributor {

    private final CustomMavenBuild build;

    private final IndentingWriterFactory indentingWriterFactory;

    private final CustomMavenBuildWriter buildWriter;

    public CustomMavenBuildContributor(CustomMavenBuild build, IndentingWriterFactory indentingWriterFactory) {
        this.build = build;
        this.indentingWriterFactory = indentingWriterFactory;
        this.buildWriter = new CustomMavenBuildWriter();
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {

        Path rootPomFile = Files.createFile(projectRoot.resolve("pom.xml"));
        writeBuild(Files.newBufferedWriter(rootPomFile));

        build.setModules(null);

        Path apiPath = initializerProjectDirectory(projectRoot, build.apiArtifactId());
        Path apiPomFile = Files.createFile(apiPath.resolve("pom.xml"));
        writeBuild(Files.newBufferedWriter(apiPomFile), build.getApiMavenBuild());

        Path webPath = initializerProjectDirectory(projectRoot, build.webArtifactId());
        Path webPomFile = Files.createFile(webPath.resolve("pom.xml"));
        writeBuild(Files.newBufferedWriter(webPomFile), build.getWebMavenBuild());
    }

    @Override
    public void writeBuild(Writer out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("maven", out)) {
            this.buildWriter.writeTo(writer, this.build);
        }
    }

    public void writeBuild(Writer out, MavenBuild build) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("maven", out)) {
            this.buildWriter.writeTo(writer, (CustomMavenBuild) build);
        }
    }

    private Path initializerProjectDirectory(Path rootDir, String dir) throws IOException {
        Path projectDirectory = rootDir.resolve(dir);
        Files.createDirectories(projectDirectory);
        return projectDirectory;
    }
}
