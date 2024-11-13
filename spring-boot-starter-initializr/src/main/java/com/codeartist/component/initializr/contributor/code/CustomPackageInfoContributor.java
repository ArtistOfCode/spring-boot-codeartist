package com.codeartist.component.initializr.contributor.code;

import com.codeartist.component.initializr.contributor.CustomMavenBuild;
import io.spring.initializr.generator.language.SourceStructure;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * package-info.java生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/6
 */
public class CustomPackageInfoContributor implements ProjectContributor {

    private final ProjectDescription description;
    private final CustomMavenBuild build;

    public CustomPackageInfoContributor(ProjectDescription description, CustomMavenBuild build) {
        this.description = description;
        this.build = build;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        String packageName = description.getPackageName();
        Path apiPath = projectRoot.resolve(build.apiArtifactId());
        Path webPath = projectRoot.resolve(build.webArtifactId());

        writePackageInfo(apiPath, packageName + ".api.entity");
        writePackageInfo(apiPath, packageName + ".api.feign");

        writePackageInfo(webPath, packageName + ".config");
        writePackageInfo(webPath, packageName + ".controller");
        writePackageInfo(webPath, packageName + ".service");
        writePackageInfo(webPath, packageName + ".mapper");
        writePackageInfo(webPath, packageName + ".entity");
    }

    private void writePackageInfo(Path apiPath, String packageName) throws IOException {
        SourceStructure structure = new SourceStructure(apiPath.resolve("src/main/"), new JavaLanguage());
        Path path = structure.createSourceFile(packageName, "package-info");

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path))) {
            writer.println("package " + packageName + ";");
        }
    }
}
