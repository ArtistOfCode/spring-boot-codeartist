package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.sample.entity.UserRole;
import com.codeartist.component.core.sample.entity.UserRoleRel;
import com.codeartist.component.core.support.curd.relation.AbstractRelationController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基本信息 控制器
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Tag(name = "UserRoleController", description = "用户关联信息")
@RestController
@RequestMapping("/api/user/role")
public class UserRoleController extends AbstractRelationController<UserRole, UserRoleRel> {
}
