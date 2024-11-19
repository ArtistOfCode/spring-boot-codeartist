package com.codeartist.component.generator.entity;

import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.codeartist.component.generator.engine.EnhanceH2Query;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码生成配置项
 *
 * @author AiJiangnan
 * @date 2022/8/2
 */
@Getter
@Setter
public class GenerateProperties {

    private IDbQuery dbQuery;

    private String url;
    private String username;
    private String password;
    private String projectPath;
    private String packageName;
    private String[] tables;
    private String[] tablesPrefix;

    public void setTables(String... tables) {
        this.tables = tables;
    }

    public void setTablesPrefix(String... tablesPrefix) {
        this.tablesPrefix = tablesPrefix;
    }

    public static GenerateProperties h2() {
        GenerateProperties prop = new GenerateProperties();
        prop.setDbQuery(new EnhanceH2Query());
        prop.setUrl("jdbc:h2:mem:default;MODE=MySQL;DATABASE_TO_LOWER=TRUE;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'");
        prop.setUsername("sa");
        prop.setPassword("");
        return prop;
    }
}
