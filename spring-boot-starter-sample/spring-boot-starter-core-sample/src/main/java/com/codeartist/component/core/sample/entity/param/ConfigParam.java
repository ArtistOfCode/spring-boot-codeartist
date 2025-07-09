package com.codeartist.component.core.sample.entity.param;

import com.codeartist.component.core.entity.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 系统配置信息
 *
 * @author CodeGenerator
 * @since 2025-07-09
 */
@Getter
@Setter
@Schema(description = "系统配置信息")
public class ConfigParam extends PageParam {

    private Long id;

    @Schema(description = "配置名称")
    private String name;

    @Schema(description = "配置值")
    private String configValue;

    @Schema(description = "状态：1：删除，0：有效")
    private Integer deleted;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
