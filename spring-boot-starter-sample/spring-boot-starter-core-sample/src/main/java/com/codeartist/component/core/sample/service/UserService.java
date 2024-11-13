package com.codeartist.component.core.sample.service;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import com.codeartist.component.core.support.curd.AbstractService;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息 服务实现类
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Service
public class UserService extends AbstractService<User, UserVO, UserParam> {
}
