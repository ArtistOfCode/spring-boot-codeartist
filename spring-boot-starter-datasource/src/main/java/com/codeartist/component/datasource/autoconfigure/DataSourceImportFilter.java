package com.codeartist.component.datasource.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 自动配置过滤，使用多数据源的时候过滤掉SpringBoot默认的数据源配置
 *
 * @author AiJiangnan
 * @date 2024/8/2
 */
public class DataSourceImportFilter implements AutoConfigurationImportFilter {

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = !DataSourceAutoConfiguration.class.getName().equals(autoConfigurationClasses[i]);
        }
        return matches;
    }
}
