package com.codeartist.component.core.support.flow;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    private static final String CHECKER_TASK_NAME = "#Check";
    private static final String PRE_TASK_NAME = "#Pre";
    private static final String POST_TASK_NAME = "#Post";

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handler个数超过当前值，使用Map进行缓存
     */
    @Value("${spring.handler.cache.max.size:10}")
    private Integer handlerCacheMaxSize;

    @Autowired(required = false)
    private List<BizChecker<P, C>> bizCheckers = Collections.emptyList();
    @Autowired(required = false)
    private List<BizConsumer.Pre<P, C>> preBizConsumers = Collections.emptyList();
    @Autowired(required = false)
    private List<BizConsumer.Post<P, C>> postBizConsumers = Collections.emptyList();

    private Map<Enum<?>, List<Handler<C>>> checkerMap = new HashMap<>();
    private Map<Enum<?>, List<Handler<C>>> preConsumerMap = new HashMap<>();
    private Map<Enum<?>, List<Handler<C>>> postConsumerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        this.initHandlerMap(bizCheckers, checkerMap);
        this.initHandlerMap(preBizConsumers, preConsumerMap);
        this.initHandlerMap(postBizConsumers, postConsumerMap);
    }

    @Override
    public void basicCheck(P param) {
        SpringContext.validate(param);
    }

    @SuppressWarnings("unchecked")
    @Override
    public C createContext(P param) {
        DefaultContext<P, R> context = new DefaultContext<>();
        context.setParam(param);
        context.setErrors(new BeanPropertyBindingResult(context, "context"));
        return (C) context;
    }

    @Override
    public void businessCheck(C context) {
        this.doHandler(context, CHECKER_TASK_NAME, getBizCheckers(), getCheckerMap());
        if (context.getErrors().hasErrors()) {
            throw new BusinessException(GlobalErrorCode.GLOBAL_BUSINESS_ERROR, context.getErrors());
        }
    }

    @Override
    public void close(C context) {
        StopWatch stopWatch = context.getStopWatch();
        if (stopWatch.getTotalTimeMillis() > 100) {
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
        this.doHandler(context, PRE_TASK_NAME, getPreBizConsumers(), getPreConsumerMap());
    }

    protected void postConsumer(C context) {
        this.doHandler(context, POST_TASK_NAME, getPostBizConsumers(), getPostConsumerMap());
    }

    protected void publishEvent(C context) {
        SpringContext.publishEvent(new BizEvent<>(this, context));
    }

    private void initHandlerMap(List<? extends Handler<C>> bizHandlers, Map<Enum<?>, List<Handler<C>>> handlerMap) {
        if (bizHandlers.size() < handlerCacheMaxSize) {
            return;
        }

        for (Handler<C> handler : bizHandlers) {
            if (handler.getAction() == null || handler.getAction().length == 0) {
                logger.warn("init Handler {} action is null.", handler.getBeanName());
                continue;
            }
            for (Enum<?> action : handler.getAction()) {
                List<Handler<C>> handlers = handlerMap.getOrDefault(action, new ArrayList<>());
                handlers.add(handler);
                logger.debug("Register Handler for action:{}, bean:{}", action, handler.getBeanName());
                handlerMap.put(action, handlers);
            }
        }

        if (logger.isDebugEnabled() && !handlerMap.isEmpty()) {
            handlerMap.forEach((action, handlers) ->
                    logger.debug("Register Handlers for action: {}, list:{}", action, handlers.stream()
                            .map(Handler::getBeanName).collect(Collectors.toList())));
        }
    }

    private void doHandler(C context, String taskName, List<? extends Handler<C>> bizHandlers, Map<Enum<?>, List<Handler<C>>> handlerMap) {
        if (CollectionUtils.isEmpty(handlerMap)) {
            List<? extends Handler<C>> results = bizHandlers.stream()
                    .filter(h -> filterHandler(h, context))
                    .peek(h -> doStopWatch(h.getBeanName() + taskName, context, h))
                    .collect(Collectors.toList());
            if (taskName.equals(CHECKER_TASK_NAME)) {
                collectCheckerErrors(results, context);
            }
            return;
        }

        List<Handler<C>> handlers = handlerMap.get(context.getAction());
        List<? extends Handler<C>> results;
        if (CollectionUtils.isEmpty(handlers)) {
            results = bizHandlers.stream()
                    .filter(h -> filterHandler(h, context))
                    .peek(h -> doStopWatch(h.getBeanName() + taskName, context, h))
                    .collect(Collectors.toList());
        } else {
            results = handlers.stream()
                    .filter(Handler::isEnabled)
                    .peek(h -> doStopWatch(h.getBeanName() + taskName, context, h))
                    .collect(Collectors.toList());
        }
        if (taskName.equals(CHECKER_TASK_NAME)) {
            collectCheckerErrors(results, context);
        }
    }

    private void collectCheckerErrors(List<? extends Handler<C>> checkers, C context) {
        if (CollectionUtils.isEmpty(checkers)) {
            return;
        }
        checkers.stream()
                .map(c -> (BizChecker<P, C>) c)
                .filter(c -> Objects.nonNull(c.getErrors()) && c.getErrors().hasErrors())
                .forEach(c -> context.getErrors().addAllErrors(c.getErrors()));
    }

    private boolean filterHandler(Handler<C> handler, C context) {
        if (!handler.isEnabled()) {
            return false;
        }
        if (handler.getAction() == null || handler.getAction().length == 0) {
            logger.warn("Handler {} action is null.", handler.getBeanName());
            return false;
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
