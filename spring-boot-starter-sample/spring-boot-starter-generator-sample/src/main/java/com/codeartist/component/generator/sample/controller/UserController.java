package com.codeartist.component.generator.sample.controller;

import com.codeartist.component.core.support.curd.AbstractController;
import com.codeartist.component.generator.sample.entity.param.UserParam;
import com.codeartist.component.generator.sample.entity.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基本信息 控制器
 *
 * @author CodeGenerator
 * @since 2023-12-14
 */
@Tag(name = "UserController", description = "用户基本信息")
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<UserVO, UserParam> {

}
