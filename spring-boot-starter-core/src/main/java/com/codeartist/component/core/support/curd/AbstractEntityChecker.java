package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.flow.AbstractBizChecker;
import com.codeartist.component.core.support.flow.BizChecker;

/**
 * 实体业务检查
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public abstract class AbstractEntityChecker<P, D> extends AbstractBizChecker<P, EntityContext<P, D>>
        implements EntityHandler, BizChecker<P, EntityContext<P, D>> {
}
