package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.entity.Relation;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import com.codeartist.component.core.sample.service.UserRoleService;
import com.codeartist.component.core.support.curd.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户基本信息 控制器
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Tag(name = "UserController", description = "用户基本信息")
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<UserVO, UserParam> {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/role")
    @Operation(summary = "查询关联信息")
    public Relation relation(Long id) {
        return userRoleService.get(id);
    }

    @PostMapping("/role")
    @Operation(summary = "保存关联接口")
    public void relation(@RequestBody Relation param) {
        userRoleService.save(param);
    }
}
