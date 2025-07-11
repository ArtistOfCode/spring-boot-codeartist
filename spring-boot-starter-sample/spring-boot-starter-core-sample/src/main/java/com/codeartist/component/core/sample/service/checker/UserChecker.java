package com.codeartist.component.core.sample.service.checker;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.support.curd.AbstractEntityChecker;
import com.codeartist.component.core.support.curd.EntityAction;
import com.codeartist.component.core.support.curd.EntityContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 用户检查
 *
 * @author AiJiangnan
 * @date 2023-12-12
 */
@Component
@Order(1)
public class UserChecker extends AbstractEntityChecker<UserParam, User> {

    @Override
    public EntityAction[] getAction() {
        return new EntityAction[]{EntityAction.SAVE};
    }

    @Override
    public void doAccept(EntityContext<UserParam, User> context) {
        rejectValue("name", "error.code");
        System.out.println("UserChecker.doAccept");
    }
}
