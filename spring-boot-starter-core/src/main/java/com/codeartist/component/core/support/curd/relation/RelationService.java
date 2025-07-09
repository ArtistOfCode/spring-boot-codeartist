package com.codeartist.component.core.support.curd.relation;

import com.codeartist.component.core.entity.Relation;

/**
 * 关联表操作接口
 *
 * @author AiJiangnan
 * @date 2023/4/23
 */
public interface RelationService {

    Relation get(Relation param);

    void save(Relation param);

    void delete(Relation param);
}
