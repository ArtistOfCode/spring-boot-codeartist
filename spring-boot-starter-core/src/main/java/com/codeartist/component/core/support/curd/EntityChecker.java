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
        stopWatch.start(getClass().getSimpleName());
        if (context.isSave()) {
            checkSave(context);
        } else if (context.isUpdate()) {
            checkUpdate(context);
        } else if (context.isDelete()) {
            checkDelete(context);
        }
        stopWatch.stop();
    }
}
