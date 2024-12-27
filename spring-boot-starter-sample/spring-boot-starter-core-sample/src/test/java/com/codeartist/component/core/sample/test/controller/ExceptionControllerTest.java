package com.codeartist.component.core.sample.test.controller;

import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.util.JSON;
import com.codeartist.component.test.AbstractSpringWebRunnerTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 接口异常测试
 *
 * @author AiJiangnan
 * @date 2023/6/25
 */
public class ExceptionControllerTest extends AbstractSpringWebRunnerTests {

    @Test
    void client() throws Exception {
        mockMvc.perform(get("/api/exception/client"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name()))
                .andDo(print());
    }

    @Test
    void business() throws Exception {
        mockMvc.perform(get("/api/exception/business"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(GlobalErrorCode.GLOBAL_BUSINESS_ERROR.name()))
                .andDo(print());
    }

    @Test
    void server() throws Exception {
        mockMvc.perform(get("/api/exception/server"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value(GlobalErrorCode.GLOBAL_SERVICE_ERROR.name()))
                .andDo(print());
    }

    @Test
    void error() throws Exception {
        UserParam param = new UserParam();
        mockMvc.perform(post("/api/exception/error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(GlobalErrorCode.GLOBAL_CLIENT_ERROR.name()))
                .andDo(print());
    }
}
