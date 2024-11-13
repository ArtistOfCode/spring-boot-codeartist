package com.codeartist.component.core.entity.event;

import com.codeartist.component.core.support.curd.EntityContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * 实体上下文事件
 *
 * @author AiJiangnan
 * @date 2023/6/7
 */
@Setter
@Getter
@JsonIgnoreProperties({"source", "timestamp"})
public class EntityEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    private EntityContext<?, T> entityContext;

    public EntityEvent(Object source, EntityContext<?, T> entityContext) {
        super(source);
        this.entityContext = entityContext;
    }

    @JsonIgnore
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(),
                ResolvableType.forInstance(getEntityContext().getEntity()));
    }
}
