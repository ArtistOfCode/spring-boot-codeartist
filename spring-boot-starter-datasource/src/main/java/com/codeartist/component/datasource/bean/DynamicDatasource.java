package com.codeartist.component.datasource.bean;

import com.codeartist.component.datasource.context.DataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author AiJiangnan
 * @date 2024/8/1
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.get();
    }
}
