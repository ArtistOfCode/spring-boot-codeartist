package com.codeartist.component.core.support.curd;

import java.util.List;

/**
 * 基础转换接口
 *
 * @param <D> 数据库实体类型
 * @param <P> 参数实体类型
 * @param <R> 结果实体类型
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
public interface BaseConverter<D, P, R> {

    D toDo(P param);

    R toVo(D param);

    List<R> toVo(List<D> param);
}
