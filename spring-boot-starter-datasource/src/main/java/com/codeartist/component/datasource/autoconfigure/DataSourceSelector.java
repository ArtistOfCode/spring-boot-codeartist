package com.codeartist.component.datasource.autoconfigure;

import com.codeartist.component.datasource.annotation.EnableDataSource;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * 多数据源模式选择：多数据源和动态数据源
 *
 * @author AiJiangnan
 * @date 2024/8/2
 */
public class DataSourceSelector implements ImportSelector {

    public static final String DYNAMIC = "dynamic";

    @NonNull
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes multiDatasourceAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableDataSource.class.getName()));

        if (multiDatasourceAttrs == null) {
            return new String[0];
        }

        boolean dynamic = multiDatasourceAttrs.getBoolean(DYNAMIC);

        if (dynamic) {
            return new String[]{DynamicDataSourceRegister.class.getName()};
        } else {
            return new String[]{MultiDataSourceRegister.class.getName()};
        }
    }
}
