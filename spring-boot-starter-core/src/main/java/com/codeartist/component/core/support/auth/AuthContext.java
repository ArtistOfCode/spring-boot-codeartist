package com.codeartist.component.core.support.auth;

import com.codeartist.component.core.entity.Principal;

import java.util.Objects;

/**
 * 权限上下文
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public interface AuthContext {

    Long getUserId();

    Principal getPrincipal();

    default Long getRequiredUserId() {
        return Objects.requireNonNull(getUserId(), "UserId is null");
    }

    default Principal getRequiredPrincipal() {
        return Objects.requireNonNull(getPrincipal(), "Principal is null");
    }
}
