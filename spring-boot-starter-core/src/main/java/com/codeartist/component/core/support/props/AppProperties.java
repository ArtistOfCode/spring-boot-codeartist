package com.codeartist.component.core.support.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * App配置
 *
 * @author AiJiangnan
 * @date 2024-02-26
 */
@Data
@ConfigurationProperties(prefix = "spring.application")
public class AppProperties {

    private String name;
    private String rootPackage;
}
