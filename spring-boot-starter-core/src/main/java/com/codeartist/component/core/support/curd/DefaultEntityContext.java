package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.support.business.DefaultContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体操作上下文默认实现
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
@Getter
@Setter
public class DefaultEntityContext<P, D> extends DefaultContext<P, D> implements EntityContext<P, D> {

    private EntityAction action;
    private D entity;
    private D oldEntity;

    public DefaultEntityContext(EntityAction action) {
        super(action);
        this.action = action;
    }

    @Override
    public void close() {
        super.close();
        setEntity(null);
        setOldEntity(null);
    }
}
