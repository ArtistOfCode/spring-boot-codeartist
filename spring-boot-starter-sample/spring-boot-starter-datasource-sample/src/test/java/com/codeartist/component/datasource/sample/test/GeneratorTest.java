package com.codeartist.component.datasource.sample.test;

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
        GenerateProperties prop = new GenerateProperties();
        prop.setUrl("jdbc:mysql://10.0.188.215:3306/test_db_2");
        prop.setUsername("dev");
        prop.setPassword("19950121");
        prop.setPackageName("com.codeartist.component.datasource.sample.db2");
        prop.setTables("t_user_info");
        GenerateUtils.generate(prop);
    }
}
