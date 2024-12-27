package com.codeartist.component.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 非Web环境基础测试类
 *
 * @author AiJiangnan
 * @date 2024/12/27
 */

@ActiveProfiles({"junit", "local"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AbstractSpringRunnerTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
