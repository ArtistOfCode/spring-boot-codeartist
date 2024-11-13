package com.codeartist.component.core.support.curd;

import org.springframework.util.StopWatch;

/**
 * 实体上下文检查抽象类
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityChecker<P, D> {

    default void checkSave(EntityContext<P, D> context) {
    }

    default void checkUpdate(EntityContext<P, D> context) {
        checkSave(context);
    }

    default void checkDelete(EntityContext<P, D> context) {
    }

    default void check(EntityContext<P, D> context) {
        StopWatch stopWatch = context.getStopWatch();
        stopWatch.start(getClass().getSimpleName() + " " + context.getAction().name());
        switch (context.getAction()) {
            case SAVE:
                checkSave(context);
                break;
            case UPDATE:
                checkUpdate(context);
                break;
            case DELETE:
                checkDelete(context);
                break;
        }
        stopWatch.stop();
    }
}
