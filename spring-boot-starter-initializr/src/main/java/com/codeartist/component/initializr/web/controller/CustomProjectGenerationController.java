package com.codeartist.component.initializr.web.controller;

import com.codeartist.component.initializr.generator.CustomProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;
import java.util.Map;

/**
 * 自定义项目脚手架控制器
 *
 * @author AiJiangnan
 * @date 2023/6/4
 */
@Controller
public class CustomProjectGenerationController extends ProjectGenerationController<ProjectRequest> {

    public CustomProjectGenerationController(InitializrMetadataProvider metadataProvider,
                                             ObjectProvider<ProjectRequestPlatformVersionTransformer> platformVersionTransformer,
                                             ApplicationContext applicationContext) {
        super(metadataProvider, new ProjectGenerationInvoker<ProjectRequest>(
                applicationContext, new DefaultProjectRequestToDescriptionConverter(platformVersionTransformer
                .getIfAvailable(DefaultProjectRequestPlatformVersionTransformer::new))) {
            @Override
            protected ProjectAssetGenerator<Path> getProjectAssetGenerator(ProjectDescription description) {
                return new CustomProjectAssetGenerator();
            }
        });
    }

    @Override
    public ProjectRequest projectRequest(Map<String, String> headers) {
        WebProjectRequest request = new WebProjectRequest();
        request.getParameters().putAll(headers);
        request.initialize(getMetadata());
        return request;
    }
}
