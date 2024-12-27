package com.codeartist.component.core.support.business;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.business.BizConsumer.BizChecker;
import com.codeartist.component.core.support.business.BizConsumer.PostConsumer;
import com.codeartist.component.core.support.business.BizConsumer.PreConsumer;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.Arrays;

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
public abstract class AbstractHandler<P, R, C extends Context<P, R>> implements BizHandler<P, R, C> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectProvider<BizChecker<P, R, C>> bizCheckers;
    @Autowired
    private ObjectProvider<PreConsumer<P, R, C>> preConsumers;
    @Autowired
    private ObjectProvider<PostConsumer<P, R, C>> postConsumers;

    @SuppressWarnings("unchecked")
    @Override
    public C createContext(P param) {
        DefaultContext<P, R> context = new DefaultContext<>();
        context.setParam(param);
        return (C) context;
    }

    @Override
    public void basicCheck(P param) {
        SpringContext.validate(param);
    }

    @Override
    public void businessCheck(C context) {
        acceptConsumer(getBizCheckers(), context);
    }

    @Override
    public void preConsumer(C context) {
        acceptConsumer(getPreConsumers(), context);
    }

    @Override
    public void postConsumer(C context) {
        acceptConsumer(getPostConsumers(), context);
    }

    @Override
    public void publishEvent(C context) {
        SpringContext.publishEvent(new BizEvent<>(this, context));
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

    protected void acceptConsumer(ObjectProvider<? extends BizConsumer<P, R, C>> consumers, C context) {
        consumers.stream()
                .filter(consumer -> filterConsumer(consumer, context))
                .forEach(consumer -> consumer.accept(context));
    }

    private boolean filterConsumer(BizConsumer<P, R, C> consumer, C context) {
        if (consumer.getAction() == null || consumer.getAction().length == 0) {
            return true;
        }
        return Arrays.stream(consumer.getAction()).anyMatch(action -> action == context.getAction());
    }
}
