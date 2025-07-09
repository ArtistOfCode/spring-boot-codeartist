package com.codeartist.component.core.sample.entity.vo;

import com.codeartist.component.core.support.props.LocalConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置信息
 *
 * @author CodeGenerator
 * @since 2025-07-09
 */
@Getter
@Setter
@Schema(description = "系统配置信息")
public class ConfigVO implements LocalConfig {

    @Schema(description = "配置名称")
    private String key;

    @Schema(description = "配置值")
    private String value;
}
