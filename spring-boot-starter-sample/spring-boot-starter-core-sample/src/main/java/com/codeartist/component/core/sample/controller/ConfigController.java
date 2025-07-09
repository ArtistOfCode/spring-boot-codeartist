package com.codeartist.component.core.sample.controller;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.sample.entity.param.ConfigParam;
import com.codeartist.component.core.sample.entity.vo.ConfigVO;
import com.codeartist.component.core.support.curd.AbstractController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置信息 控制器
 *
 * @author CodeGenerator
 * @since 2025-07-09
 */
@Tag(name = "系统配置信息")
@RestController
@RequestMapping("/config")
public class ConfigController extends AbstractController<ConfigVO, ConfigParam> {

    @GetMapping("/property/{key}")
    public String getProperty(@PathVariable("key") String key) {
        return SpringContext.getProperty(key);
    }
}
