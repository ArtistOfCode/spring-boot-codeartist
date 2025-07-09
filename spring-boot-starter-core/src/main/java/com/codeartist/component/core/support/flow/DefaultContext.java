package com.codeartist.component.core.support.flow;

import com.codeartist.component.core.util.MsStopWatch;
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
public class DefaultContext<P, R> implements Context<P> {

    private P param;
    private R result;
    private StopWatch stopWatch;

    public DefaultContext() {
        this.stopWatch = new MsStopWatch();
    }

    @Override
    public Enum<?> getAction() {
        return BizAction.DEFAULT;
    }

    @Override
    public void close() {
        setParam(null);
    }
}
