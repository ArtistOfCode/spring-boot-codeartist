package com.codeartist.component.core.entity.param;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 数据变更操作人参数接口
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
public interface UpdateParam {

    @Schema(hidden = true)
    default void setCreateUser(Long id) {
    }

    @Schema(hidden = true)
    default void setUpdateUser(Long id) {
    }
}
