package com.codeartist.component.core.support.auth;

import com.codeartist.component.core.entity.Principal;
import com.codeartist.component.core.util.WebUtils;

import java.util.Optional;

/**
 * 默认权限上下文实现
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public class DefaultAuthContext implements AuthContext {

    public Long getUserId() {
        return Optional.ofNullable(getPrincipal()).map(Principal::getId).orElse(null);
    }

    @Override
    public Principal getPrincipal() {
        return WebUtils.getSession(Principal.class.getName());
    }
}
