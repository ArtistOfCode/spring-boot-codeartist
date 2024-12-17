package com.codeartist.component.core.support.business;

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
public class BizEvent<P> extends PayloadApplicationEvent<P> {

    private Context<P, ?> context;

    public BizEvent(Object source, Context<P, ?> context) {
        super(source, context.getParam());
        this.context = context;
    }
}
