package com.codeartist.component.core.sample.test.util;


import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.test.AbstractSpringWebRunnerTests;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.realm.MessageDigestCredentialHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 加密测试类
 *
 * @author AiJiangnan
 * @date 2024/11/19
 */
@Slf4j
public class CredentialTest extends AbstractSpringWebRunnerTests {

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

    @Test
    void testRandom() {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        bindingResult.rejectValue("name", "name.null");
        bindingResult.reject("user.null");
        bindingResult.getAllErrors().forEach(error -> log.error(SpringContext.getMessage(error)));
    }
}