package com.codeartist.component.initializr.contributor.code;

import io.spring.initializr.generator.language.CompilationUnit;
import io.spring.initializr.generator.language.SourceCode;
import io.spring.initializr.generator.language.SourceCodeWriter;
import io.spring.initializr.generator.language.TypeDeclaration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.code.TestApplicationTypeCustomizer;
import io.spring.initializr.generator.spring.code.TestSourceCodeCustomizer;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 单元测试源码生成配置
 *
 * @author AiJiangnan
 * @date 2023/6/6
 */
public class CustomTestSourceContributor<T extends TypeDeclaration, C extends CompilationUnit<T>, S extends SourceCode<T, C>>
        implements ProjectContributor {

    private final ProjectDescription description;

    private final Supplier<S> sourceFactory;

    private final SourceCodeWriter<S> sourceWriter;

    private final ObjectProvider<TestApplicationTypeCustomizer<?>> testApplicationTypeCustomizers;

    private final ObjectProvider<TestSourceCodeCustomizer<?, ?, ?>> testSourceCodeCustomizers;

    public CustomTestSourceContributor(ProjectDescription description, Supplier<S> sourceFactory,
                                       SourceCodeWriter<S> sourceWriter,
                                       ObjectProvider<TestApplicationTypeCustomizer<?>> testApplicationTypeCustomizers,
                                       ObjectProvider<TestSourceCodeCustomizer<?, ?, ?>> testSourceCodeCustomizers) {
        this.description = description;
        this.sourceFactory = sourceFactory;
        this.sourceWriter = sourceWriter;
        this.testApplicationTypeCustomizers = testApplicationTypeCustomizers;
        this.testSourceCodeCustomizers = testSourceCodeCustomizers;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        S sourceCode = this.sourceFactory.get();
        String testName = this.description.getApplicationName() + "Tests";
        C compilationUnit = sourceCode.createCompilationUnit(this.description.getPackageName() + ".test", testName);
        T testApplicationType = compilationUnit.createTypeDeclaration(testName);
        customizeTestApplicationType(testApplicationType);
        customizeTestSourceCode(sourceCode);
        this.sourceWriter.writeTo(
                this.description.getBuildSystem().getTestSource(projectRoot, this.description.getLanguage()),
                sourceCode);
    }

    @SuppressWarnings("unchecked")
    private void customizeTestApplicationType(TypeDeclaration testApplicationType) {
        List<TestApplicationTypeCustomizer<?>> customizers = this.testApplicationTypeCustomizers.orderedStream()
                .collect(Collectors.toList());
        LambdaSafe.callbacks(TestApplicationTypeCustomizer.class, customizers, testApplicationType)
                .invoke((customizer) -> customizer.customize(testApplicationType));
    }

    @SuppressWarnings("unchecked")
    private void customizeTestSourceCode(S sourceCode) {
        List<TestSourceCodeCustomizer<?, ?, ?>> customizers = this.testSourceCodeCustomizers.orderedStream()
                .collect(Collectors.toList());
        LambdaSafe.callbacks(TestSourceCodeCustomizer.class, customizers, sourceCode)
                .invoke((customizer) -> customizer.customize(sourceCode));
    }

}
