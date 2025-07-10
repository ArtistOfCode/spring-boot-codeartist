package com.codeartist.component.core.sample.controller;


import com.codeartist.component.core.sample.feign.PostmanFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AiJiangnan
 * @date 2025/7/10
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private PostmanFeignService postmanFeignService;

    @GetMapping("/get")
    public String get() {
        return postmanFeignService.get("codeartist");
    }
}
