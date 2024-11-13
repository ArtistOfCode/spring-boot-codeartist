package com.codeartist.component.generator.sample.service;

import com.codeartist.component.core.support.curd.AbstractService;
import com.codeartist.component.generator.sample.entity.User;
import com.codeartist.component.generator.sample.entity.param.UserParam;
import com.codeartist.component.generator.sample.entity.vo.UserVO;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息 服务实现类
 *
 * @author CodeGenerator
 * @since 2023-12-14
 */
@Service
public class UserService extends AbstractService<User, UserVO, UserParam> {

}
