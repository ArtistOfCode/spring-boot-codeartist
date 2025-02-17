package com.codeartist.component.core.support.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.PayloadApplicationEvent;

/**
 * 业务操作事件
 *
 * @author AiJiangnan
 * @date 2023/6/7
 */
@Setter
@Getter
@JsonIgnoreProperties({"source", "timestamp"})
public class BizEvent<P> extends PayloadApplicationEvent<P> {

    private Context<P> context;

    public BizEvent(Object source, Context<P> context) {
        super(source, context.getParam());
        this.context = context;
    }
}
