package com.codeartist.component.datasource.sample;

import com.codeartist.component.datasource.annotation.EnableDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.codeartist.component.core.entity.enums.GlobalConstants.ROOT_PACKAGE_KEY;

/**
 * @author AiJiangnan
 * @date 2024/8/2
 */
@SpringBootApplication(proxyBeanMethods = false)
@EnableDataSource
@MapperScans({
        @MapperScan(value = ROOT_PACKAGE_KEY + ".db1.mapper", sqlSessionTemplateRef = "db1SqlSessionTemplate"),
        @MapperScan(value = ROOT_PACKAGE_KEY + ".db2.mapper", sqlSessionTemplateRef = "db2SqlSessionTemplate"),
})
public class DataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceApplication.class, args);
    }
}
