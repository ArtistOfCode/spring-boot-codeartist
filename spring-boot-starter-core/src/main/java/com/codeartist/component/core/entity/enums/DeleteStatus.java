package com.codeartist.component.core.entity.enums;

import com.codeartist.component.core.entity.Bitmask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 逻辑删除状态
 */
@Getter
@RequiredArgsConstructor
public enum DeleteStatus implements Bitmask {

    /**
     * 是否有效
     */
    VALID(1);

    private final int bit;
}
