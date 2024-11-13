package com.codeartist.component.core.support.auth;

/**
 * 权限上下文
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public interface AuthContext {

    Long getRequiredUserId();

    Long getUserId();
}
