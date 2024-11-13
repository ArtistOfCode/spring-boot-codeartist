package com.codeartist.component.generator.sample.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户基本信息
 *
 * @author CodeGenerator
 * @since 2023-12-14
 */
@Getter
@Setter
@TableName("t_user")
public class User {

    private Long id;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态：1：删除，0：有效
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
