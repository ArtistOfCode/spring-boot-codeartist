package com.codeartist.component.core.support.business;

import com.codeartist.component.core.SpringContext;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * 抽象服务类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractHandler<P, R, C extends DefaultContext<P, R>> implements BaseHandler<P, R> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectProvider<BizChecker<P, R, C>> bizCheckers;
    @Autowired
    private ObjectProvider<PreConsumer<P, R, C>> preConsumers;
    @Autowired
    private ObjectProvider<PostConsumer<P, R, C>> postConsumers;
    @Autowired
    private ObjectProvider<TransactionTemplate> transactionTemplate;

    /**
     * 创建上下文
     */
    protected abstract C createContext();

    protected abstract void doExecute(C context);

    @Override
    public R execute(P param) {
        basicCheck(param);

        C context = createContext();
        context.setParam(param);

        // 带有事务的业务
        getTransactionTemplate().executeWithoutResult(status -> {
            businessCheck(context);

            preConsumer(context);
            doExecute(context);
            postConsumer(context);

            SpringContext.publishEvent(new BizEvent<>(this, context));
            doFinally(context);
        });
        return context.getResult();
    }

    /**
     * 获取事务操作接口（只允许一个事务Bean存在）
     */
    protected TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate.getIfUnique();
    }

    /**
     * 参数基础校验
     */
    protected void basicCheck(P param) {
        SpringContext.validate(param);
    }

    /**
     * 业务校验
     */
    private void businessCheck(C context) {
        bizCheckers.stream()
                .filter(consumer -> filterAction(consumer, context))
                .forEach(checker -> checker.accept(context));
    }

    /**
     * 执行前置处理
     */
    private void preConsumer(C context) {
        preConsumers.stream()
                .filter(consumer -> filterAction(consumer, context))
                .forEach(consumer -> consumer.accept(context));
    }

    /**
     * 执行后置处理
     */
    private void postConsumer(C context) {
        postConsumers.stream()
                .filter(consumer -> filterAction(consumer, context))
                .forEach(consumer -> consumer.accept(context));
    }

    /**
     * 过滤Action处理
     */
    private boolean filterAction(BizConsumer<P, R, C> consumer, C context) {
        return Arrays.stream(consumer.getAction()).anyMatch(action -> action == context.getAction());
    }

    /**
     * 执行最终处理
     */
    private void doFinally(C context) {
        StopWatch stopWatch = context.getStopWatch();
        if (stopWatch.getTotalTimeMillis() > 200) {
            log.info(stopWatch.prettyPrint());
        } else {
            log.info(stopWatch.shortSummary());
        }
        context.clear();
    }
}
