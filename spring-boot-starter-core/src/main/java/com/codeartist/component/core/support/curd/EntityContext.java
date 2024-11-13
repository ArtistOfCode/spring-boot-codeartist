package com.codeartist.component.core.support.curd;

import org.springframework.util.StopWatch;

/**
 * 实体操作上下文
 *
 * @author AiJiangnan
 * @date 2023-12-09
 */
public interface EntityContext<P, D> {

    boolean isSave();

    void setSave(boolean save);

    boolean isUpdate();

    void setUpdate(boolean update);

    boolean isDelete();

    void setDelete(boolean delete);

    P getParam();

    void setParam(P param);

    D getEntity();

    void setEntity(D entity);

    D getOldEntity();

    void setOldEntity(D entity);

    StopWatch getStopWatch();

    default void clear() {
        setParam(null);
        setEntity(null);
        setOldEntity(null);
    }
}
