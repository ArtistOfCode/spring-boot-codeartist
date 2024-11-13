package com.codeartist.component.sdk.wechat.pay;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付SDK-V3
 *
 * @author AiJiangnan
 * @date 2024/6/14
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WechatPayProperties.class)
public class WechatPayAutoConfiguration {

    @Bean
    public WechatNativePayService wechatNativePayService(WechatPayProperties properties) {
        return new WechatNativePayService(properties);
    }
}
