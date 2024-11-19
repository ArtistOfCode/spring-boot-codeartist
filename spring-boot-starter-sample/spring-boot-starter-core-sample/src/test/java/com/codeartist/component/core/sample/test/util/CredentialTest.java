package com.codeartist.component.core.sample.test.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.realm.MessageDigestCredentialHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 加密测试类
 *
 * @author AiJiangnan
 * @date 2024/11/19
 */
@Slf4j
public class CredentialTest {

    @Test
    void mockLogin() throws NoSuchAlgorithmException {
        MessageDigestCredentialHandler credentialHandler = new MessageDigestCredentialHandler();
        credentialHandler.setAlgorithm("MD5");
        credentialHandler.setSaltLength(16);
        String password = UUID.randomUUID().toString();
        String storePassword = credentialHandler.mutate(password);
        log.info(storePassword);
        boolean success = credentialHandler.matches(password, storePassword);
        Assertions.assertTrue(success);
    }
}