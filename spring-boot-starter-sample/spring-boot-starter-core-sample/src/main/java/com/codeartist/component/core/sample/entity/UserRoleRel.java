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
public class UserRoleRel extends Relation {

    public UserRoleRel() {
        super("user_id", "role_id");
    }

    public static class Invert extends Relation {

        public Invert() {
            super("user_id", "role_id", true);
        }
    }
}
