package com.codeartist.component.core.support.curd.handler;


import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.curd.*;

/**
 * 更新实体处理器
 *
 * @author AiJiangnan
 * @date 2025/3/20
 */
public class UpdateEntityHandler<P extends PageParam, D, R> extends AbstractEntityHandler<P, D, R> {

    private final AbstractService<P, D, R> service;

    public UpdateEntityHandler(AbstractService<P, D, R> abstractService) {
        super(EntityAction.UPDATE, abstractService);
        this.service = abstractService;
    }

    @Override
    public void basicCheck(P param) {
        if (param.getId() == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }
        super.basicCheck(param);
    }

    @Override
    public EntityContext<P, D> createContext(P param) {
        DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) super.createContext(param);
        Long userId = service.getAuthContext().getUserId();
        param.setUpdateUser(userId);

        D old = service.getMapper().selectById(param.getId());
        context.setOldEntity(old);
        if (old == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }
        return context;
    }

    @Override
    public void execute(EntityContext<P, D> context) {
        service.getMapper().updateById(context.getEntity());
    }
}
