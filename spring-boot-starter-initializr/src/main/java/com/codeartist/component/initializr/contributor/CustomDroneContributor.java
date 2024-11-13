package com.codeartist.component.initializr.contributor;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * .drone.yml生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/5
 */
public class CustomDroneContributor implements ProjectContributor {

    private final ProjectDescription description;

    public CustomDroneContributor(ProjectDescription description) {
        this.description = description;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        String name = description.getName();
        Path droneFile = Files.createFile(projectRoot.resolve(".drone.yml"));
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(droneFile))) {
            writer.println("kind: template");
            writer.println("load: service_pipeline_v1.yaml");
            writer.println("data:");
            writer.printf("  name: %s%n", name);
            writer.printf("  jar: %s-web-1.0.0.jar%n", name);
            writer.printf("  output: %s-web/target%n", name);
        }
    }
}
