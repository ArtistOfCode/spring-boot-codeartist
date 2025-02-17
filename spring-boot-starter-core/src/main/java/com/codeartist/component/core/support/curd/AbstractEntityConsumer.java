package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.flow.AbstractBizConsumer;
import com.codeartist.component.core.support.flow.BizConsumer;

/**
 * 扩展处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public abstract class AbstractEntityConsumer<P, D> extends AbstractBizConsumer<P, EntityContext<P, D>>
        implements EntityHandler, BizConsumer<P, EntityContext<P, D>> {
}
