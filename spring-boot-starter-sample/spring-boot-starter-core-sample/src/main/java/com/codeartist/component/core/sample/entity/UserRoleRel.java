package com.codeartist.component.core.sample.entity;

import com.codeartist.component.core.entity.Relation;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关联信息
 *
 * @author CodeGenerator
 * @since 2024-11-19
 */
@Getter
@Setter
public class UserRoleRel extends Relation<UserRole> {

    public UserRoleRel() {
        super(UserRole::getUserId, UserRole::getRoleId);
    }

    @Override
    public UserRole toRelEntity(Long more) {
        UserRole userRole = new UserRole();
        userRole.setUserId(getId());
        userRole.setRoleId(more);
        return userRole;
    }
}
