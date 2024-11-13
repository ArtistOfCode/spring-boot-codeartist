package com.codeartist.component.core.sample.entity.param;

import com.codeartist.component.core.entity.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 用户基本信息
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Getter
@Setter
@Schema(description = "用户基本信息")
public class UserParam extends PageParam {

    private Long id;

    @NotBlank
    @Schema(description = "真实姓名")
    private String name;

    @NotBlank
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态：1：删除，0：有效")
    private Integer deleted;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
