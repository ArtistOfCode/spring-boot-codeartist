package com.codeartist.component.core.support.curd.relation;

import com.codeartist.component.core.entity.Relation;

/**
 * 关联表操作接口
 *
 * @author AiJiangnan
 * @date 2023/4/23
 */
public interface RelationService<D> {

    Relation<D> get(Relation<D> param);

    void save(Relation<D> param);

    void delete(Relation<D> param);
}
