package com.codeartist.component.autoconfigure.feign;

import com.codeartist.component.core.entity.enums.GlobalConstants;
import feign.Feign;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Feign全局自动配置
 *
 * @author AiJiangnan
 * @date 2022/11/3
 */
@ConditionalOnClass(Feign.class)
@EnableFeignClients(GlobalConstants.ROOT_PACKAGE_KEY + ".feign")
public class FeignClientsConfiguration {

    @Bean
    public ErrorDecoder feignRpcErrorDecoder() {
        return new FeignRpcErrorDecoder();
    }
}
