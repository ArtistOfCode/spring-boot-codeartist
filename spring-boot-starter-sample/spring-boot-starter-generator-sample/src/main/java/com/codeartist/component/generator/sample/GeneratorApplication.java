package com.codeartist.component.generator.sample;

import com.codeartist.component.core.entity.enums.GlobalConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成组件测试
 *
 * @author AiJiangnan
 * @date 2023/6/12
 */
@SpringBootApplication
@MapperScan(GlobalConstants.ROOT_PACKAGE_KEY + ".mapper")
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
