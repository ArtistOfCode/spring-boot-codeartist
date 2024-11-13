package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeartist.component.core.entity.Relation;
import org.apache.ibatis.annotations.Param;

/**
 * 关联表数据库操作
 *
 * @author AiJiangnan
 * @date 2023/6/28
 */
public interface RelationMapper<T> extends BaseMapper<T> {

    void insertBatch(@Param("param") Relation param, @Param("column") boolean column);
}
