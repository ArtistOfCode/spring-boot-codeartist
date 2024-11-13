package com.codeartist.component.core.support.auth;

import com.codeartist.component.core.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 默认权限上下文实现
 *
 * @author AiJiangnan
 * @date 2023-11-12
 */
public class DefaultAuthContext implements AuthContext {

    @Override
    public Long getRequiredUserId() {
        Long userId = getUserId();
        Assert.notNull(userId, "UserId is null.");
        return userId;
    }

    public Long getUserId() {
        String userId = WebUtils.getRequestHeader("UserId");
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }
}
