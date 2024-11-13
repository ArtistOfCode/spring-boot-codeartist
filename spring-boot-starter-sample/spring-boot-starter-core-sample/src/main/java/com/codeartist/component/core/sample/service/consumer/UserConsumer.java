package com.codeartist.component.core.sample.service.consumer;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.support.curd.EntityConsumer;
import com.codeartist.component.core.support.curd.EntityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户处理
 *
 * @author AiJiangnan
 * @date 2023-12-12
 */
@Slf4j
@Component
public class UserConsumer implements EntityConsumer<UserParam, User> {

    @Override
    public void preSave(EntityContext<UserParam, User> context) {
        log.info(context.toString());
    }
}
