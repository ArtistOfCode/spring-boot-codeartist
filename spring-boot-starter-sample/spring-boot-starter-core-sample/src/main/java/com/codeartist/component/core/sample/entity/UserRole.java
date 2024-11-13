package com.codeartist.component.core.sample.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关联信息
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Getter
@Setter
@TableName("t_user_role")
public class UserRole {

    private Long id;

    /**
     * 真实姓名
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private Long roleId;
}
