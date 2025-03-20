package com.codeartist.component.test;


import com.codeartist.component.core.entity.enums.Environments.Profiles;
import com.codeartist.component.core.util.JSON;
import com.codeartist.component.test.mock.MockAutoConfiguration;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

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
public abstract class AbstractSpringWebRunnerTests {

    protected static final Faker faker = new Faker();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MockMvc mockMvc;

    protected Consumer<MockHttpServletRequestBuilder> getMockHttpRequestConsumer() {
        return builder -> {
        };
    }

    protected MockHttpServletRequestBuilder doGet(String uri, Object... uriVars) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(uri, uriVars);
        getMockHttpRequestConsumer().accept(builder);
        return builder;
    }

    protected MockHttpServletRequestBuilder doPost(String uri, Object body, Object... uriVars) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri, uriVars)
                .contentType(MediaType.APPLICATION_JSON);
        Optional.ofNullable(body).ifPresent(b -> builder.content(JSON.toJSONString(b)));
        getMockHttpRequestConsumer().accept(builder);
        return builder;
    }

    protected MockHttpServletRequestBuilder doPut(String uri, Object body, Object... uriVars) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(uri, uriVars)
                .contentType(MediaType.APPLICATION_JSON);
        Optional.ofNullable(body).ifPresent(b -> builder.content(JSON.toJSONString(b)));
        getMockHttpRequestConsumer().accept(builder);
        return builder;
    }

    protected MockHttpServletRequestBuilder doDelete(String uri, Object... uriVars) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete(uri, uriVars);
        getMockHttpRequestConsumer().accept(builder);
        return builder;
    }

    protected ResultActions request(RequestBuilder builder) {
        try {
            ResultActions actions = mockMvc.perform(builder);
            actions.andDo(result -> logger.info(result.getResponse().getContentAsString(StandardCharsets.UTF_8)));
            return actions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
