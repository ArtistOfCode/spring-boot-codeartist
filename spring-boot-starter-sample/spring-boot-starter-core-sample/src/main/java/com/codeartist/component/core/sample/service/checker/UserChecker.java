package com.codeartist.component.core.sample.service.checker;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.support.curd.EntityChecker;
import com.codeartist.component.core.support.curd.EntityContext;
import org.springframework.stereotype.Component;

/**
 * 用户检查
 *
 * @author AiJiangnan
 * @date 2023-12-12
 */
@Component
public class UserChecker implements EntityChecker<UserParam, User> {

    @Override
    public void checkSave(EntityContext<UserParam, User> context) {
        EntityChecker.super.checkSave(context);
    }
}
