package com.codeartist.component.core.support.business;

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
public class DefaultContext<P, R> implements Context<P, R> {

    private P param;
    private R result;
    private StopWatch stopWatch;

    public DefaultContext() {
        this.stopWatch = new StopWatch();
    }

    public DefaultContext(Enum<?> action) {
        this.stopWatch = new StopWatch(action.name().toLowerCase());
    }

    @Override
    public Enum<?> getAction() {
        return BizAction.DEFAULT;
    }

    @Override
    public void clear() {
        setParam(null);
    }
}
