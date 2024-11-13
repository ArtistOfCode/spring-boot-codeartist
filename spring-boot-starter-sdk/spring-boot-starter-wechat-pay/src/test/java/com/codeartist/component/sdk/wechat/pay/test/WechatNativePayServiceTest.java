package com.codeartist.component.sdk.wechat.pay.test;

import com.codeartist.component.sdk.wechat.pay.WechatNativePayService;
import com.codeartist.component.sdk.wechat.pay.WechatPayAutoConfiguration;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


/**
 * @author AiJiangnan
 * @date 2024/6/14
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WechatPayAutoConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class WechatNativePayServiceTest {

    @Autowired
    private WechatNativePayService wechatNativePayService;

    @Test
    void prepay() {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(10);
        request.setAmount(amount);
        request.setOutTradeNo(UUID.randomUUID().toString().replaceAll("-", ""));
        request.setDescription("单元测试商品标题");
        PrepayResponse response = wechatNativePayService.prepay(request);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getCodeUrl());
        log.info("Pay code url: {}", response.getCodeUrl());
    }
}