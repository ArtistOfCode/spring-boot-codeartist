package com.codeartist.component.core.support.auth;


import com.codeartist.component.core.entity.Principal;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口权限验证
 *
 * @author AiJiangnan
 * @date 2025/3/11
 */
public interface ApiAuthTemplate {

    /**
     * 获取权限上下文
     */
    AuthContext getAuthContext();

    /**
     * 判断接口是否需要验证
     */
    boolean needVerify(HttpServletRequest request);

    /**
     * 判断接口是否验证
     */
    default void verify() throws BadRequestException {
        Principal principal = getAuthContext().getPrincipal();
        if (principal == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_UNAUTHORIZED);
        }
    }
}
