package com.codeartist.component.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Web工具类
 *
 * @author AiJiangnan
 * @date 2020/9/8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUtils {

    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        String unknown = "unknown";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        int length = 15;
        if (ip != null && ip.length() > length) {
            String str = ",";
            if (ip.indexOf(str) > 0) {
                ip = ip.substring(0, ip.indexOf(str));
            }
        }
        return ip;
    }

    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(att -> (ServletRequestAttributes) att)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new IllegalStateException("Request info error."));
    }

    public static HttpServletResponse getResponse() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(att -> (ServletRequestAttributes) att)
                .map(ServletRequestAttributes::getResponse)
                .orElseThrow(() -> new IllegalStateException("Response info error."));
    }

    public static String getRequestHeader(String key) {
        return getRequest().getHeader(key);
    }
}
