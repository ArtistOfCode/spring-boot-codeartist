package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.business.AbstractHandler;
import com.codeartist.component.core.support.curd.EntityConsumer.EntityChecker;
import com.codeartist.component.core.support.curd.EntityConsumer.PostEntityConsumer;
import com.codeartist.component.core.support.curd.EntityConsumer.PreEntityConsumer;
import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.beans.factory.ObjectProvider;

/**
 * 数据库实体处理器抽象实现，整个生命周期接口
 *
 * @param <P> 参数实体
 * @param <D> 数据库实体
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractEntityHandler<P extends PageParam, D> extends AbstractHandler<P, D, EntityContext<P, D>> {

    private final EntityAction action;
    private final Logger logger;
    private final BaseConverter<D, P, ?> converter;
    private final AuthContext authContext;
    private final ObjectProvider<EntityChecker<P, D>> entityCheckers;
    private final ObjectProvider<PreEntityConsumer<P, D>> preEntityConsumers;
    private final ObjectProvider<PostEntityConsumer<P, D>> postEntityConsumers;

    public AbstractEntityHandler(EntityAction action, AbstractService<D, ?, P> abstractService) {
        this.action = action;
        this.logger = abstractService.getLogger();
        this.converter = abstractService.getConverter();
        this.authContext = abstractService.getAuthContext();
        this.entityCheckers = abstractService.getEntityCheckers();
        this.preEntityConsumers = abstractService.getPreEntityConsumers();
        this.postEntityConsumers = abstractService.getPostEntityConsumers();
    }

    @Override
    public EntityContext<P, D> createContext(P param) {
        DefaultEntityContext<P, D> context = new DefaultEntityContext<>(getAction());
        context.setParam(param);

        Long userId = authContext.getUserId();

        if (context.getAction() == EntityAction.SAVE) {
            param.setCreateUser(userId);
        }
        param.setUpdateUser(userId);

        D entity = getConverter().toDo(param);
        context.setEntity(entity);

        return context;
    }

    @Override
    public void businessCheck(EntityContext<P, D> context) {
        super.acceptConsumer(getEntityCheckers(), context);
    }

    @Override
    public void preConsumer(EntityContext<P, D> context) {
        super.acceptConsumer(getPreEntityConsumers(), context);
    }

    @Override
    public void postConsumer(EntityContext<P, D> context) {
        super.acceptConsumer(getPostEntityConsumers(), context);
    }

    @Override
    public void publishEvent(EntityContext<P, D> context) {
        SpringContext.publishEvent(new EntityEvent<>(this, context));
    }
}
