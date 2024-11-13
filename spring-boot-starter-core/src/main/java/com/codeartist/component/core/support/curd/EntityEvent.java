package com.codeartist.component.core.support.curd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.PayloadApplicationEvent;

/**
 * 实体上下文事件
 *
 * @author AiJiangnan
 * @date 2023/6/7
 */
@Setter
@Getter
@JsonIgnoreProperties({"source", "timestamp"})
public class EntityEvent<T> extends PayloadApplicationEvent<T> {

    private EntityContext<?, T> entityContext;

    public EntityEvent(Object source, EntityContext<?, T> entityContext) {
        super(source, entityContext.getEntity());
        this.entityContext = entityContext;
    }
}
