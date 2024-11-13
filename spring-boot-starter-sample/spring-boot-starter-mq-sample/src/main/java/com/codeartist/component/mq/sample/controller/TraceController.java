package com.codeartist.component.mq.sample.controller;

import brave.ScopedSpan;
import brave.Tracer;
import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.support.mq.MqMessage;
import com.codeartist.component.core.support.mq.MqType;
import com.codeartist.component.mq.sample.entity.Example;
import com.codeartist.component.mq.sample.entity.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author AiJiangnan
 * @date 2023/7/31
 */
@Slf4j
@RestController
@RequestMapping("/api/trace")
public class TraceController {

    @Autowired
    private Tracer tracer;

    @GetMapping
    public void trace() {
        log.info("Step 1.");
        ScopedSpan span = tracer.startScopedSpan("test");
        try {
            log.info("Step 2.");
        } finally {
            span.finish();
        }
        log.info("Step 3.");
    }

    @GetMapping("/send")
    public void send() {
        Example example = new Example();
        example.setName("AiJiangnan");
        example.setAge(28L);
        example.setPercent(BigDecimal.TEN);
        example.setCount(0);

        SpringContext.publishEvent(MqMessage.builder()
                .type(MqType.REDIS)
                .topic(Topics.REDIS_TOPIC)
                .body(example)
                .build());
    }
}
