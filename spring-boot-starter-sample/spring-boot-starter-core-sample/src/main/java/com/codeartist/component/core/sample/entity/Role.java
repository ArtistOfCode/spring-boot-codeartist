package com.codeartist.component.core.sample.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色信息
 *
 * @author CodeGenerator
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("t_role")
public class Role {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 真实姓名
     */
    private String name;
}
