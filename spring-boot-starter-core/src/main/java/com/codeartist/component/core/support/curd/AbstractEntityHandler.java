package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.business.AbstractHandler;
import com.codeartist.component.core.support.curd.EntityConsumer.EntityChecker;
import com.codeartist.component.core.support.curd.EntityConsumer.PostEntityConsumer;
import com.codeartist.component.core.support.curd.EntityConsumer.PreEntityConsumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;

/**
 * 业务处理器抽象实现，整个生命周期接口
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractEntityHandler<P extends PageParam, D, C extends DefaultEntityContext<P, D>>
        extends AbstractHandler<P, D, C> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EntityAction action;
    private final BaseConverter<D, P, ?> converter;
    private final AuthContext authContext;
    private final ObjectProvider<EntityChecker<P, D, EntityContext<P, D>>> entityCheckers;
    private final ObjectProvider<PreEntityConsumer<P, D, EntityContext<P, D>>> preEntityConsumers;
    private final ObjectProvider<PostEntityConsumer<P, D, EntityContext<P, D>>> postEntityConsumers;

    @SuppressWarnings("unchecked")
    @Override
    public C createContext(P param) {
        DefaultEntityContext<P, D> context = new DefaultEntityContext<>(getAction());
        context.setParam(param);

        Long userId = authContext.getUserId();
        param.setCreateUser(userId);
        param.setUpdateUser(userId);

        D entity = getConverter().toDo(param);
        context.setEntity(entity);

        return (C) context;
    }

    @Override
    public void businessCheck(C context) {
        super.acceptConsumer(getEntityCheckers(), context);
    }

    @Override
    public void preConsumer(C context) {
        super.acceptConsumer(getPreEntityConsumers(), context);
    }

    @Override
    public void postConsumer(C context) {
        super.acceptConsumer(getPostEntityConsumers(), context);
    }

    @Override
    public void publishEvent(C context) {
        SpringContext.publishEvent(new EntityEvent<>(this, context));
    }
}
