package com.codeartist.component.core.sample.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_role")
public class Role {

    private Long id;

    /**
     * 真实姓名
     */
    private String name;
}
