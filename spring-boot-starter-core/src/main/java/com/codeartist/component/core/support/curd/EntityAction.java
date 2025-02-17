package com.codeartist.component.core.support.curd;


/**
 * 实体类业务动作类型
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
public enum EntityAction {
    /**
     * 通过主键查询单个
     */
    GET,
    /**
     * 分页查询
     */
    QUERY,
    /**
     * 保存记录
     */
    SAVE,
    /**
     * 更新记录
     */
    UPDATE,
    /**
     * 删除记录
     */
    DELETE
}
