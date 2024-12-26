package com.codeartist.component.core.support.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 应用配置
 *
 * @author AiJiangnan
 * @date 2024-02-26
 */
@Data
@ConfigurationProperties(prefix = "spring.application")
public class AppProperties {

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用源码包根路径
     */
    private String rootPackage;
    /**
     * 应用子模块源码包路径
     */
    private String modulePackage;
}
