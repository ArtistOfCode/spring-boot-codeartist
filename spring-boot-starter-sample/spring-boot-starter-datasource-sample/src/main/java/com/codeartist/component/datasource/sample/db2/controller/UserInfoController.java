package com.codeartist.component.datasource.sample.db2.controller;

import com.codeartist.component.core.support.curd.AbstractController;
import com.codeartist.component.datasource.sample.db2.entity.param.UserInfoParam;
import com.codeartist.component.datasource.sample.db2.entity.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/userInfo")
public class UserInfoController extends AbstractController<UserInfoVO, UserInfoParam> {

}
