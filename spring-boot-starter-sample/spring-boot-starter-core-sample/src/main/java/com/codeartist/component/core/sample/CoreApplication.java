package com.codeartist.component.core.sample;

import com.codeartist.component.core.entity.enums.GlobalConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 核心组件测试项目
 *
 * @author AiJiangnan
 * @date 2023/6/25
 */
@SpringBootApplication
@MapperScan(GlobalConstants.ROOT_PACKAGE_KEY + ".mapper")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
