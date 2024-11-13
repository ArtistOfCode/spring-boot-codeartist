package com.codeartist.component.datasource.sample.db1.controller;

import com.codeartist.component.core.support.curd.AbstractController;
import com.codeartist.component.datasource.sample.db1.entity.param.UserParam;
import com.codeartist.component.datasource.sample.db1.entity.vo.UserVO;
import com.codeartist.component.datasource.sample.db1.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基本信息 控制器
 *
 * @author CodeGenerator
 * @since 2024-08-02
 */
@Tag(name = "用户基本信息")
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserVO, UserParam> {

    @GetMapping("/insert")
    public void insert() {
        ((UserService) getService()).insert();
    }
}
