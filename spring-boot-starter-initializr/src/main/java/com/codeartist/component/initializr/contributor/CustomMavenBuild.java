package com.codeartist.component.initializr.contributor;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;

import java.util.List;

/**
 * 自定义Maven项目构建实体
 *
 * @author AiJiangnan
 * @date 2023/6/2
 */
public class CustomMavenBuild extends MavenBuild {

    private MavenBuild apiMavenBuild;
    private MavenBuild webMavenBuild;
    private List<String> modules;

    public MavenBuild getApiMavenBuild() {
        return this.apiMavenBuild;
    }

    public void setApiMavenBuild(MavenBuild apiMavenBuild) {
        this.apiMavenBuild = apiMavenBuild;
    }

    public MavenBuild getWebMavenBuild() {
        return this.webMavenBuild;
    }

    public void setWebMavenBuild(MavenBuild webMavenBuild) {
        this.webMavenBuild = webMavenBuild;
    }

    public String apiArtifactId() {
        return getSettings().getArtifact() + "-api";
    }

    public String webArtifactId() {
        return getSettings().getArtifact() + "-web";
    }

    public List<String> modules() {
        return this.modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }
}
