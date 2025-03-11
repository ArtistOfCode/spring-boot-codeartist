package com.codeartist.component.core.support.flow;

import com.codeartist.component.core.SpringContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 业务处理器抽象实现，整个生命周期接口
 *
 * @param <P> 业务处理参数
 * @param <R> 业务处理返回值
 * @param <C> 业务处理上下文
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
@Setter
public abstract class AbstractHandler<P, R, C extends Context<P>> implements BizHandler<P, R, C> {

    private static final String EXECUTE_TASK_NAME = "Execute";
    private static final String EVENT_TASK_NAME = "Event";
    private static final String PRE_TASK_NAME = "#Pre";
    private static final String POST_TASK_NAME = "#Post";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private List<BizChecker<P, C>> bizCheckers = Collections.emptyList();
    @Autowired(required = false)
    private List<BizConsumer<P, C>> bizConsumers = Collections.emptyList();

    @Override
    public void basicCheck(P param) {
        SpringContext.validate(param);
    }

    @SuppressWarnings("unchecked")
    @Override
    public C createContext(P param) {
        DefaultContext<P, R> context = new DefaultContext<>();
        context.setParam(param);
        return (C) context;
    }

    @Override
    public void businessCheck(C context) {
        getBizCheckers().stream()
                .filter(consumer -> filterConsumer(consumer, context))
                .forEach(consumer -> doStopWatch(consumer.getBeanName(), context, consumer));
    }

    @Override
    public void close(C context) {
        StopWatch stopWatch = context.getStopWatch();
        if (stopWatch.getTotalTimeMillis() > 200) {
            getLogger().info(stopWatch.prettyPrint());
        } else {
            getLogger().info(stopWatch.shortSummary());
        }
        context.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public R apply(P param) {

        basicCheck(param);

        C context = createContext(param);

        try {
            businessCheck(context);

            preConsumer(context);

            doStopWatch(EXECUTE_TASK_NAME, context, this::execute);

            postConsumer(context);

            doStopWatch(EVENT_TASK_NAME, context, this::publishEvent);

            return (R) context.getResult();
        } finally {
            close(context);
        }
    }

    protected void preConsumer(C context) {
        getBizConsumers().stream()
                .filter(consumer -> filterConsumer(consumer, context))
                .forEach(consumer -> doStopWatch(consumer.getBeanName() + PRE_TASK_NAME, context,
                        consumer::preConsumer));
    }

    protected void postConsumer(C context) {
        getBizConsumers().stream()
                .filter(consumer -> filterConsumer(consumer, context))
                .forEach(consumer -> doStopWatch(consumer.getBeanName() + POST_TASK_NAME, context,
                        consumer::postConsumer));
    }

    protected void publishEvent(C context) {
        SpringContext.publishEvent(new BizEvent<>(this, context));
    }

    private boolean filterConsumer(Handler handler, C context) {
        if (!handler.isEnabled()) {
            return false;
        }
        if (handler.getAction() == null || handler.getAction().length == 0) {
            return true;
        }
        return Arrays.stream(handler.getAction()).anyMatch(action -> action == context.getAction());
    }

    private void doStopWatch(String taskName, C context, Consumer<C> consumer) {
        StopWatch stopWatch = context.getStopWatch();
        stopWatch.start(taskName);
        try {
            consumer.accept(context);
        } finally {
            stopWatch.stop();
        }
    }
}
