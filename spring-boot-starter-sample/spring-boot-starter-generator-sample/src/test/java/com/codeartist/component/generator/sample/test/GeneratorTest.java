package com.codeartist.component.generator.sample.test;

import com.codeartist.component.generator.GenerateUtils;
import com.codeartist.component.generator.entity.GenerateProperties;
import org.junit.jupiter.api.Test;

/**
 * 代码生成单元测试
 *
 * @author AiJiangnan
 * @date 2023/6/12
 */
public class GeneratorTest {

    @Test
    void generate() {
        GenerateProperties prop = GenerateProperties.h2();
        prop.setPackageName("com.codeartist.component.generator.sample");
        prop.setTables("t_user");
        GenerateUtils.generate(prop);
    }
}
