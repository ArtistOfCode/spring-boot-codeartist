package com.codeartist.component.core.sample.test.annotation;

import com.codeartist.component.core.sample.test.AbstractSpringWebRunnerTests;
import com.codeartist.component.core.support.props.AppProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础注解测试
 *
 * @author AiJiangnan
 * @date 2023-12-11
 */
public class AnnotationTest extends AbstractSpringWebRunnerTests {

    @Autowired
    private AppProperties appProperties;

    @Test
    void annotation() {
        Assertions.assertEquals("core-sample", appProperties.getName());
        Assertions.assertEquals("com.codeartist.component.core.sample", appProperties.getRootPackage());
    }
}
