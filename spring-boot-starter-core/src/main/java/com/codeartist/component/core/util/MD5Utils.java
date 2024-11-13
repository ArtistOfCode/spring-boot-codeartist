package com.codeartist.component.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * MD5工具类
 *
 * @author AiJiangnan
 * @date 2022/7/25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MD5Utils {

    private static final MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(byte[] data) {
        byte[] digest = md5.digest(data);
        byte[] encode = Base64.getEncoder().encode(digest);
        return new String(encode, StandardCharsets.UTF_8);
    }

    public static String md5(String data) {
        return md5(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5(String data, String salt) {
        return md5(data + salt);
    }
}
