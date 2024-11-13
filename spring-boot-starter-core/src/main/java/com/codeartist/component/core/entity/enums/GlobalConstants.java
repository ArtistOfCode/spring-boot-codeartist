package com.codeartist.component.core.entity.enums;

/**
 * 全局常量
 *
 * @author AiJiangnan
 * @date 2020/10/10
 */
public interface GlobalConstants {

    /**
     * 应用服务名称
     */
    String APPLICATION_NAME_KEY = "${spring.application.name}";
    /**
     * 应用服务包的根路径
     */
    String ROOT_PACKAGE_KEY = "${spring.application.root-package}";
    /**
     * 应用服务子模块包的根路径
     */
    String MODULE_PACKAGE_KEY = "#{'${spring.application.module-package:}'.split(',')}";

    /**
     * 默认标识
     */
    String DEFAULT = "default";
}
