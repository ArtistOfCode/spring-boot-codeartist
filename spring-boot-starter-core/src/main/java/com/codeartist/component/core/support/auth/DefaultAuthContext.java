package com.codeartist.component.core.support.auth;

import com.codeartist.component.core.entity.Principal;
import com.codeartist.component.core.util.WebUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * 默认权限上下文实现
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public class DefaultAuthContext implements AuthContext {

    @Override
    public void setPrincipal(Principal principal) {
        WebUtils.getRequest().getSession().setAttribute(Principal.class.getName(), principal);
    }

    public Long getUserId() {
        return Optional.ofNullable(getPrincipal()).map(Principal::getId).orElse(null);
    }

    @Override
    public Principal getPrincipal() {
        return WebUtils.getSession(Principal.class.getName());
    }

    @Override
    public Long getRequiredUserId() {
        return Objects.requireNonNull(getUserId(), "UserId is null");
    }

    @Override
    public Principal getRequiredPrincipal() {
        return Objects.requireNonNull(getPrincipal(), "Principal is null");
    }

    @Override
    public void invalidate() {
        WebUtils.getRequest().getSession().invalidate();
    }
}
