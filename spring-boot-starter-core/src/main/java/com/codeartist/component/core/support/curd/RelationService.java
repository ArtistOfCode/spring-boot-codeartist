package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.entity.Relation;

/**
 * 关联表操作接口
 *
 * @author AiJiangnan
 * @date 2023/4/23
 */
public interface RelationService {

    Relation get(Long id, boolean column);

    void save(Relation param, boolean column);

    void delete(Long id, boolean column);

    default Relation get(Long id) {
        return get(id, true);
    }

    default void save(Relation param) {
        save(param, true);
    }

    default void delete(Long id) {
        delete(id, true);
    }
}
