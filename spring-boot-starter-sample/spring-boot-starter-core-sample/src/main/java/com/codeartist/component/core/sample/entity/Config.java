package com.codeartist.component.core.sample.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_config")
public class Config {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 状态：1：删除，0：有效
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
