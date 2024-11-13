package com.codeartist.component.core.sample.service;

import com.codeartist.component.core.sample.entity.UserRole;
import com.codeartist.component.core.support.curd.AbstrartRelationService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联信息 服务实现类
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Service
public class UserRoleService extends AbstrartRelationService<UserRole> {

    protected UserRoleService() {
        super(UserRole::getUserId, UserRole::getRoleId);
    }
}
