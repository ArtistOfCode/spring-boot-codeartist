package com.codeartist.component.core.entity;

import java.time.Duration;
import java.util.List;

/**
 * 当前登录用户信息
 *
 * @author 艾江南
 * @date 2021/10/14
 */
public interface Principal {

    Long getId();

    String getName();

    String getUsername();

    Duration getExpire();

    List<String> getPaths();
}
