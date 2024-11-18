package com.codeartist.component.generator.sample.test;

import com.codeartist.component.generator.GenerateUtils;
import com.codeartist.component.generator.engine.EnhanceH2Query;
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
        prop.setDbQuery(new EnhanceH2Query());
        prop.setUrl("jdbc:h2:mem:default;MODE=MySQL;DATABASE_TO_LOWER=TRUE;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'");
        prop.setUsername("sa");
        prop.setPassword("");
        prop.setPackageName("com.codeartist.component.generator.sample");
        prop.setTables("t_user");
        GenerateUtils.generate(prop);
    }
}
