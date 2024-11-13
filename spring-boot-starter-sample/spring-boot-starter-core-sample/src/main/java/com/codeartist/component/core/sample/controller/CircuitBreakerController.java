package com.codeartist.component.core.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 熔断降级接口
 *
 * @author AiJiangnan
 * @date 2024/6/18
 */
@RestController
@RequestMapping("/circuit-breaker")
public class CircuitBreakerController {

    @GetMapping
    public ResponseEntity<String> circuitBreaker(@RequestParam Integer status) {
        return ResponseEntity.status(status).body("Fail");
    }

    @GetMapping("/timeout")
    public String timeout() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }
}
