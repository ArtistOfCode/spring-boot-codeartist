package com.codeartist.component.datasource.context;

import org.springframework.core.NamedThreadLocal;

/**
 * 动态数据源选择
 *
 * @author AiJiangnan
 * @date 2024/8/1
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> dataSourceKey = new NamedThreadLocal<>("DataSource Key");

    public static void set(String key) {
        dataSourceKey.set(key);
    }

    public static String get() {
        return dataSourceKey.get();
    }

    public static void clear() {
        dataSourceKey.remove();
    }
}
