package com.codeartist.component.core.support.auth;

import com.codeartist.component.core.entity.Principal;

/**
 * 权限上下文接口
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public interface AuthContext {

    void setPrincipal(Principal principal);

    Long getUserId();

    Principal getPrincipal();

    Long getRequiredUserId();

    Principal getRequiredPrincipal();

    void invalidate();
}
