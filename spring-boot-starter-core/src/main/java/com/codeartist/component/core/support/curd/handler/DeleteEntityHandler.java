package com.codeartist.component.core.support.curd.handler;


import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.curd.*;

/**
 * 删除实体处理器
 *
 * @author AiJiangnan
 * @date 2025/3/20
 */
public class DeleteEntityHandler<P extends PageParam, D, R> extends AbstractEntityHandler<P, D, R> {

    private final AbstractService<P, D, R> service;
    private final Long id;

    public DeleteEntityHandler(Long id, AbstractService<P, D, R> abstractService) {
        super(EntityAction.DELETE, abstractService);
        this.id = id;
        this.service = abstractService;
    }

    @Override
    public void basicCheck(P param) {
        if (id == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }
    }

    @Override
    public EntityContext<P, D> createContext(P param) {
        D old = service.getMapper().selectById(id);
        if (old == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }

        DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) super.createContext(param);
        context.setEntity(old);
        context.setOldEntity(old);
        return context;
    }

    @Override
    public void execute(EntityContext<P, D> context) {
        service.getMapper().deleteById(id);
    }
}
