package com.codeartist.component.core.support.curd;


import com.codeartist.component.core.support.flow.Handler;

/**
 * 实体操作
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
public interface EntityHandler extends Handler {

    @Override
    default EntityAction[] getAction() {
        return new EntityAction[]{EntityAction.SAVE, EntityAction.UPDATE};
    }
}
