package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.business.BizConsumer;

/**
 * 实体上下文保存前处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
@FunctionalInterface
public interface EntityConsumer<P, D> extends BizConsumer<P, D, EntityContext<P, D>> {

    /**
     * 获取可操作类型
     */
    @Override
    default EntityAction[] getAction() {
        return new EntityAction[]{EntityAction.SAVE, EntityAction.UPDATE};
    }

    /**
     * 业务检查接口
     */
    interface EntityChecker<P, D> extends BizChecker<P, D, EntityContext<P, D>>, EntityConsumer<P, D> {
    }

    /**
     * 前置处理接口
     */
    interface PreEntityConsumer<P, D> extends PreConsumer<P, D, EntityContext<P, D>>, EntityConsumer<P, D> {
    }

    /**
     * 后置处理接口
     */
    interface PostEntityConsumer<P, D> extends PostConsumer<P, D, EntityContext<P, D>>, EntityConsumer<P, D> {
    }
}
