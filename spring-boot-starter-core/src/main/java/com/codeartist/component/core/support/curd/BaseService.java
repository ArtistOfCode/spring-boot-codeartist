package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.entity.PageInfo;

/**
 * 基础服务类
 *
 * @author AiJiangnan
 * @since 2022-08-31
 */
public interface BaseService<R, P> {

    /**
     * 通过ID查询详情
     */
    R get(Long id);

    /**
     * 条件分页查询记录
     */
    PageInfo<R> get(P param);

    /**
     * 保存记录
     */
    void save(P param);

    /**
     * 更新记录
     */
    void update(P param);

    /**
     * 通过ID删除记录
     */
    void delete(Long id);
}
