package com.codeartist.component.core.sample.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
public class UserVO {

    private Long id;

    @Schema(defaultValue = "真实姓名")
    private String name;

    @Schema(defaultValue = "用户名")
    private String username;

    @Schema(defaultValue = "密码")
    private String password;

    @Schema(defaultValue = "状态：1：删除，0：有效")
    private Integer deleted;

    @Schema(defaultValue = "创建时间")
    private LocalDateTime createTime;
}
