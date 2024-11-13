package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.sample.entity.param.RoleParam;
import com.codeartist.component.core.sample.entity.vo.RoleVO;
import com.codeartist.component.core.support.curd.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色信息 控制器
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Tag(name = "RoleController", description = "角色信息")
@Validated
@RestController
@RequestMapping("/api/role")
public class RoleController extends AbstractController<RoleVO, RoleParam> {
}
