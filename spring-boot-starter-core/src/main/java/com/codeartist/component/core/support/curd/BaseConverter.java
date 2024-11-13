package com.codeartist.component.core.support.curd;

import java.util.List;

/**
 * 基础转换接口
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
public interface BaseConverter<D, P, R> {

    D toDo(P param);

    R toVo(D param);

    List<R> toVo(List<D> param);
}
