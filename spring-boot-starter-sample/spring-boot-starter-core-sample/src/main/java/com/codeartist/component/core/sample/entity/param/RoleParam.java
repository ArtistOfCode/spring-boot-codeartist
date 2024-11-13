package com.codeartist.component.core.sample.entity.param;

import com.codeartist.component.core.entity.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色信息
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Getter
@Setter
@Schema(description = "角色信息")
public class RoleParam extends PageParam {

    private Long id;

    @Schema(description = "真实姓名")
    private String name;
}
