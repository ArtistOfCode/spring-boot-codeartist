package com.codeartist.component.test;


import com.codeartist.component.core.entity.enums.Environments.Profiles;
import com.codeartist.component.test.mock.MockAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * 非Web环境基础测试类
 *
 * @author AiJiangnan
 * @date 2024/12/27
 */

@ActiveProfiles({Profiles.JUNIT, Profiles.LOCAL})
@Import(MockAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AbstractSpringRunnerTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
