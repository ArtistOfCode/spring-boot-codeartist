package com.codeartist.component.core.sample.service.consumer;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.support.curd.AbstractEntityConsumer;
import com.codeartist.component.core.support.curd.EntityAction;
import com.codeartist.component.core.support.curd.EntityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.CredentialHandler;
import org.springframework.stereotype.Component;

/**
 * 用户处理
 *
 * @author AiJiangnan
 * @date 2023-12-12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer extends AbstractEntityConsumer<UserParam, User> {

    private final CredentialHandler credentialHandler;

    @Override
    public EntityAction[] getAction() {
        return new EntityAction[]{EntityAction.SAVE};
    }

    @Override
    public void doPreConsumer(EntityContext<UserParam, User> context) {
        String password = context.getParam().getPassword();
        String mutate = credentialHandler.mutate(password);
        context.getEntity().setPassword(mutate);
    }
}
