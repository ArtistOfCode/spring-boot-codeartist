package com.codeartist.component.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

/**
 * 当前登录用户信息
 *
 * @author 艾江南
 * @date 2021/10/14
 */
@Getter
@Setter
public class Principal {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * Token过期时间
     */
    private Duration expire;

    /**
     * 拥有权限的接口，格式：<code>Method:Path</code>
     * <p>
     * 示例：<code>GET:/api/**</code>
     */
    private List<String> paths;
}
