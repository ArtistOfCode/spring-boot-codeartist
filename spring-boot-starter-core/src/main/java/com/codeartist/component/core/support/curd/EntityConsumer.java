package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.business.BizConsumer;

/**
 * 实体上下文保存前处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
@FunctionalInterface
public interface EntityConsumer<P, D, C extends EntityContext<P, D>> extends BizConsumer<P, D, C> {

    /**
     * 获取可操作类型
     */
    @Override
    default EntityAction[] getAction() {
        return new EntityAction[]{EntityAction.SAVE, EntityAction.UPDATE};
    }
}
