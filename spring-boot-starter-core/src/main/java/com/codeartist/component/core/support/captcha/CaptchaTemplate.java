package com.codeartist.component.core.support.captcha;


import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 验证码处理
 *
 * @author AiJiangnan
 * @date 2024/11/13
 */
public interface CaptchaTemplate extends Predicate<CaptchaParam>, Consumer<CaptchaParam> {
}
