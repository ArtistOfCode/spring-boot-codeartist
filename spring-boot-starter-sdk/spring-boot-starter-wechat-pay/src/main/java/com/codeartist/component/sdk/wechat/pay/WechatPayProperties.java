package com.codeartist.component.sdk.wechat.pay;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 微信支付配置
 *
 * @author AiJiangnan
 * @date 2024/6/14
 */
@Getter
@Setter
@Validated
@ConfigurationProperties("wechat.pay")
public class WechatPayProperties {

    /**
     * 商户号
     */
    @NotBlank
    private String mchId;
    /**
     * 商户API私钥
     */
    @NotBlank
    private String privateKey;
    /**
     * 商户证书序列号
     */
    @NotBlank
    private String mchNum;
    /**
     * 商户API-V3密钥
     */
    @NotBlank
    private String apiKey;
    /**
     * 公众号ID
     */
    @NotBlank
    private String appId;
    /**
     * 商户后端服务回调接口地址
     */
    @NotBlank
    private String callbackUrl;
}
