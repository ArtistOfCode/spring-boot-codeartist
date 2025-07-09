package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.sample.entity.UserRole;
import com.codeartist.component.core.sample.entity.UserRoleRel;
import com.codeartist.component.core.support.curd.relation.AbstractRelationController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色基本信息 控制器
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Tag(name = "RoleUserController", description = "角色关联信息")
@RestController
@RequestMapping("/api/role/user")
public class RoleUserController extends AbstractRelationController<UserRole, UserRoleRel.Invert> {
}
