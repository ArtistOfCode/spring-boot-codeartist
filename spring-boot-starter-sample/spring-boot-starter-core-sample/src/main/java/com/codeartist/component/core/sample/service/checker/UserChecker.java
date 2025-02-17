package com.codeartist.component.core.sample.service.checker;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.support.curd.AbstractEntityChecker;
import com.codeartist.component.core.support.curd.EntityContext;
import org.springframework.stereotype.Component;

/**
 * 用户检查
 *
 * @author AiJiangnan
 * @date 2023-12-12
 */
@Component
public class UserChecker extends AbstractEntityChecker<UserParam, User> {

    @Override
    public void doAccept(EntityContext<UserParam, User> context) {

    }
}
