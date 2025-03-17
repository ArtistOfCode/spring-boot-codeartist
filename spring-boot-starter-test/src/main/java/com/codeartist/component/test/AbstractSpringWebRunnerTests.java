package com.codeartist.component.test;


import com.codeartist.component.core.entity.enums.Environments.Profiles;
import com.codeartist.component.test.mock.MockAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.nio.charset.StandardCharsets;

/**
 * Web环境基础测试类
 *
 * @author AiJiangnan
 * @date 2024/12/27
 */

@ActiveProfiles({Profiles.JUNIT})
@Import(MockAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractSpringWebRunnerTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MockMvc mockMvc;

    protected ResultHandler print() {
        return result -> logger.info(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
}
