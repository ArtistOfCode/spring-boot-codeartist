package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.flow.AbstractBizConsumer;
import com.codeartist.component.core.support.flow.BizConsumer;

/**
 * 扩展处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public abstract class AbstractEntityConsumer {

    public static abstract class Pre<P, D> extends AbstractBizConsumer.Pre<P, EntityContext<P, D>>
            implements EntityHandler<EntityContext<P, D>>, BizConsumer.Pre<P, EntityContext<P, D>> {
    }

    public static abstract class Post<P, D> extends AbstractBizConsumer.Post<P, EntityContext<P, D>>
            implements EntityHandler<EntityContext<P, D>>, BizConsumer.Post<P, EntityContext<P, D>> {
    }
}
