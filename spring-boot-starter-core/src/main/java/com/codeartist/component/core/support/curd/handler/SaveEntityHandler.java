package com.codeartist.component.core.support.curd.handler;


import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.support.curd.AbstractEntityHandler;
import com.codeartist.component.core.support.curd.AbstractService;
import com.codeartist.component.core.support.curd.EntityAction;
import com.codeartist.component.core.support.curd.EntityContext;
import lombok.Getter;

/**
 * 保存实体处理器
 *
 * @author AiJiangnan
 * @date 2025/3/20
 */
@Getter
public class SaveEntityHandler<P extends PageParam, D, R> extends AbstractEntityHandler<P, D, R> {

    private final AbstractService<P, D, R> service;

    public SaveEntityHandler(AbstractService<P, D, R> abstractService) {
        super(EntityAction.SAVE, abstractService);
        this.service = abstractService;
    }

    @Override
    public EntityContext<P, D> createContext(P param) {
        EntityContext<P, D> context = super.createContext(param);
        Long userId = service.getAuthContext().getUserId();
        param.setCreateUser(userId);
        param.setUpdateUser(userId);
        return context;

    }

    @Override
    public void execute(EntityContext<P, D> context) {
        service.getMapper().insert(context.getEntity());
    }
}
