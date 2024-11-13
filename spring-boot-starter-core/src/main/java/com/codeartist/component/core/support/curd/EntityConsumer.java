package com.codeartist.component.core.support.curd;

import org.springframework.util.StopWatch;

/**
 * 实体上下文保存前处理器
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityConsumer<P, D> {

    default void preSave(EntityContext<P, D> context) {
    }

    default void preUpdate(EntityContext<P, D> context) {
        preSave(context);
    }

    default void preDelete(EntityContext<P, D> context) {
    }

    default void postSave(EntityContext<P, D> context) {
    }

    default void postUpdate(EntityContext<P, D> context) {
        postSave(context);
    }

    default void postDelete(EntityContext<P, D> context) {
    }

    default void preConsumer(EntityContext<P, D> context) {
        StopWatch stopWatch = context.getStopWatch();
        stopWatch.start(getClass().getSimpleName() + " Pre " + context.getAction().name());
        switch (context.getAction()) {
            case SAVE:
                preSave(context);
                break;
            case UPDATE:
                preUpdate(context);
                break;
            case DELETE:
                preDelete(context);
                break;
        }
        stopWatch.stop();
    }

    default void postConsumer(EntityContext<P, D> context) {
        StopWatch stopWatch = context.getStopWatch();
        stopWatch.start(getClass().getSimpleName() + " Post " + context.getAction().name());
        switch (context.getAction()) {
            case SAVE:
                postSave(context);
                break;
            case UPDATE:
                postUpdate(context);
                break;
            case DELETE:
                postDelete(context);
                break;
        }
        stopWatch.stop();
    }
}
