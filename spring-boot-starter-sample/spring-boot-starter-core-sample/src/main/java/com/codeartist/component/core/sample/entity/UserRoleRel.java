package com.codeartist.component.core.sample.entity;

import com.codeartist.component.core.entity.Relation;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

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
        super(UserRole::getUserId, UserRole::getRoleId, (id, ids) -> ids.stream().map(e -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            userRole.setRoleId(e);
            return userRole;
        }).collect(Collectors.toList()));
    }
}
