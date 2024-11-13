package com.codeartist.component.initializr.contributor.code;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

import java.io.IOException;
import java.nio.file.Path;

/**
 * application.yaml生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/6
 */
public class CustomApplicationYamlContributor extends SingleResourceProjectContributor {

    public CustomApplicationYamlContributor() {
        this("application.yaml");
    }

    public CustomApplicationYamlContributor(String filename) {
        super("src/main/resources/" + filename, "classpath:configuration/" + filename);
    }

    @Override
    public void contribute(Path projectRoot) {
        try {
            super.contribute(projectRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
