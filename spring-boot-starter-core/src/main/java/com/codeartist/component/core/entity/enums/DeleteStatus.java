package com.codeartist.component.core.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 逻辑删除状态
 */
@Getter
@RequiredArgsConstructor
public enum DeleteStatus {

    /**
     * 有效的
     */
    VALID(0),
    /**
     * 无效的
     */
    INVALID(1);

    private final int status;
}
