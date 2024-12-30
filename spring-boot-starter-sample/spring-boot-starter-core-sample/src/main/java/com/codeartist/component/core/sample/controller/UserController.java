package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.entity.Principal;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.curd.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    private AuthContext authContext;

    @PostMapping("login")
    public void login(@RequestBody UserParam param, HttpServletRequest request) {
        Principal principal = new Principal();
        principal.setName(param.getName());
        principal.setUsername(param.getUsername());
        authContext.setPrincipal(principal);
    }
}
