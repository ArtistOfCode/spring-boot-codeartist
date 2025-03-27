package com.codeartist.component.core.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 时间工具类
 *
 * @author AiJiangnan
 * @date 2025/3/27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

    /**
     * 获取季度
     */
    public static int getQuarter(LocalDate date) {
        return (date.getMonthValue() - 1) / 3 + 1;
    }
}
