package com.codeartist.component.core.sample.test.converter;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.converter.UserConverter;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import com.codeartist.component.core.sample.test.AbstractSpringWebRunnerTests;
import com.codeartist.component.core.util.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConverterTest extends AbstractSpringWebRunnerTests {

    @Autowired
    private UserConverter userConverter;

    @Test
    void testConvert() {
        User user = new User();
        user.setId(1L);
        user.setUsername("AiJiangnan");
        UserVO vo = userConverter.toVo(user);
        logger.info(JSON.toJSONString(vo));
    }
}
