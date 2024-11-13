package com.codeartist.component.core.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus.Series;

/**
 * 自定义业务异常HTTP状态码
 *
 * @author AiJiangnan
 * @date 2023/7/25
 */
@Getter
@RequiredArgsConstructor
public enum ApiHttpStatus {
    CLIENT_WARNING(498, Series.CLIENT_ERROR, "Client Warning"),
    BUSINESS_WARNING(499, Series.CLIENT_ERROR, "Business Warning"),
    SERVER_ERROR(599, Series.SERVER_ERROR, "Server Error"),
    ;

    private final int value;
    private final Series series;
    private final String reasonPhrase;

    public static boolean isWarning(int status) {
        return status / 100 == 4;
    }

    public static boolean isError(int status) {
        return status / 100 == 5;
    }
}
