package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.support.flow.AbstractHandler;
import com.codeartist.component.core.support.flow.BizChecker;
import com.codeartist.component.core.support.flow.BizConsumer;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 数据库实体处理器抽象实现，整个生命周期接口
 *
 * @param <P> 参数实体
 * @param <D> 数据库实体
 * @author AiJiangnan
 * @date 2023/6/1
 */
@RequiredArgsConstructor
public abstract class AbstractEntityHandler<P extends PageParam, D, R> extends AbstractHandler<P, R, EntityContext<P, D>> {

    private final EntityAction action;
    private final AbstractHandler<P, ?, EntityContext<P, D>> bizHandlerDelegate;

    @Override
    public List<BizChecker<P, EntityContext<P, D>>> getBizCheckers() {
        return bizHandlerDelegate.getBizCheckers();
    }

    @Override
    public List<BizConsumer<P, EntityContext<P, D>>> getBizConsumers() {
        return bizHandlerDelegate.getBizConsumers();
    }

    @Override
    public void basicCheck(P param) {
        this.bizHandlerDelegate.basicCheck(param);
    }

    @Override
    public EntityContext<P, D> createContext(P param) {
        DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) this.bizHandlerDelegate.createContext(param);
        context.setAction(this.action);
        return context;
    }

    @Override
    public void close(EntityContext<P, D> context) {
        this.bizHandlerDelegate.close(context);
    }

    @Override
    public void publishEvent(EntityContext<P, D> context) {
        SpringContext.publishEvent(new EntityEvent<>(this, context));
    }
}
