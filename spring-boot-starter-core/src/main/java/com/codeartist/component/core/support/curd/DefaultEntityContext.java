package com.codeartist.component.core.support.curd;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StopWatch;

/**
 * 实体操作上下文默认实现
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
@Getter
@Setter
public class DefaultEntityContext<P, D> implements EntityContext<P, D> {

    private EntityAction action;
    private P param;
    private D entity;
    private D oldEntity;
    private StopWatch stopWatch;

    public DefaultEntityContext(EntityAction action) {
        this.stopWatch = new StopWatch(action.name());
    }

    @Override
    public void clear() {
        setParam(null);
        setEntity(null);
        setOldEntity(null);
    }
}
