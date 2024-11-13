package com.codeartist.component.core.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 删除状态
 */
@Getter
@RequiredArgsConstructor
public enum DeleteStatus {

    UNDELETED(0), DELETED(1);

    private final int status;
}
