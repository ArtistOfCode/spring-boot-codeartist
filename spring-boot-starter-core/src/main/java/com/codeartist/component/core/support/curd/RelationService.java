package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.entity.Relation;

import java.util.List;
import java.util.function.Function;

/**
 * 关联表操作接口
 *
 * @author AiJiangnan
 * @date 2023/4/23
 */
public interface RelationService<D> {

    Relation get(Long id, Function<D, Long> field);

    void save(List<D> param, Function<D, Long> field);

    void delete(Long id, Function<D, Long> field);
}
