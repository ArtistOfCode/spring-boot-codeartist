package com.codeartist.component.datasource.sample.db2.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户基本信息
 *
 * @author CodeGenerator
 * @since 2024-08-02
 */
@Getter
@Setter
@Schema(description = "用户基本信息")
public class UserInfoVO {

    private Long id;

    @Schema(description = "真实姓名")
    private String name;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态：1：删除，0：有效")
    private Integer deleted;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
