package com.codeartist.component.core.sample.test.util;

import com.codeartist.component.core.util.MD5Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author AiJiangnan
 * @date 2022/11/24
 */
class MD5UtilsTest {

    @Test
    public void md5() {
        String data = UUID.randomUUID().toString();
        String md5 = MD5Utils.md5(data);
        Assertions.assertNotNull(md5);

        data = UUID.randomUUID().toString();
        String salt = UUID.randomUUID().toString();
        md5 = MD5Utils.md5(data, salt);
        Assertions.assertNotNull(md5);
    }
}