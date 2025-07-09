package com.codeartist.component.core.sample.test.util;

import com.codeartist.component.generator.GenerateUtils;
import com.codeartist.component.generator.entity.GenerateProperties;

/**
 * 代码生成单元测试
 *
 * @author AiJiangnan
 * @date 2023/6/12
 */
public class Generator {

    public static void main(String[] args) {
        GenerateProperties prop = GenerateProperties.h2();
        prop.setProjectPath("/Users/aijiangnan/code/codeartist/spring-boot-codeartist/spring-boot-starter-sample/spring-boot-starter-core-sample");
        prop.setPackageName("com.codeartist.component.core.sample");
        prop.setTables("t_config");
        GenerateUtils.generate(prop);
    }
}
