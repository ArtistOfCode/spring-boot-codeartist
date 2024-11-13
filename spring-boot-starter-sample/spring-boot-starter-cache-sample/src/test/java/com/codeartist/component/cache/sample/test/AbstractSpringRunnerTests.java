package com.codeartist.component.cache.sample.test;

import com.codeartist.component.cache.sample.CacheApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SpringBoot Web单元测试
 *
 * @author AiJiangnan
 * @date 2020/7/15
 */
@SpringBootTest(classes = CacheApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class AbstractSpringRunnerTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
