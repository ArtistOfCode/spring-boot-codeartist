package com.codeartist.component.sdk.wechat.pay;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;

/**
 * 微信本地支持接口
 *
 * @author AiJiangnan
 * @date 2024/6/14
 */
@RequiredArgsConstructor
public class WechatNativePayService implements InitializingBean {

    private final WechatPayProperties properties;
    private NativePayService service;

    @Override
    public void afterPropertiesSet() {
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(properties.getMchId())
                        .privateKey(properties.getPrivateKey())
                        .merchantSerialNumber(properties.getMchNum())
                        .apiV3Key(properties.getApiKey())
                        .build();
        service = new NativePayService.Builder().config(config).build();
    }

    public PrepayResponse prepay(PrepayRequest request) {
        request.setAppid(properties.getAppId());
        request.setMchid(properties.getMchId());
        request.setNotifyUrl(properties.getCallbackUrl());
        return service.prepay(request);
    }

    public void closeOrder(CloseOrderRequest request) {
        request.setMchid(properties.getMchId());
        service.closeOrder(request);
    }

    public Transaction queryOrderById(QueryOrderByIdRequest request) {
        request.setMchid(properties.getMchId());
        return service.queryOrderById(request);
    }

    public Transaction queryOrderByOutTradeNo(QueryOrderByOutTradeNoRequest request) {
        request.setMchid(properties.getMchId());
        return service.queryOrderByOutTradeNo(request);
    }
}
