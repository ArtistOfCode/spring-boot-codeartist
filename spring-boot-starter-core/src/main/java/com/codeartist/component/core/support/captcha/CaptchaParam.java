package com.codeartist.component.core.support.captcha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码校验参数
 *
 * @author 艾江南
 * @date 2023/3/8
 */
public interface CaptchaParam {

    @Schema(description = "验证码校验类型")
    CaptchaType getType();

    @Schema(description = "验证码校验Key")
    String getKey();

    @Schema(description = "验证码校验Value")
    String getCode();

    @Getter
    @Setter
    class Default implements CaptchaParam {
        private CaptchaType type;
        private String key;
        private String code;
    }
}
