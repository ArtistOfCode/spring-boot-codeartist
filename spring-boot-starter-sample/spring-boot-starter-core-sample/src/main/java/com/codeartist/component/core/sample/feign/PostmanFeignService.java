package com.codeartist.component.core.sample.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author AiJiangnan
 * @date 2025/7/10
 */
@FeignClient(name = "postman", url = "https://postman-echo.com")
public interface PostmanFeignService {

    @GetMapping("/get")
    String get(@RequestParam("name") String name);
}
