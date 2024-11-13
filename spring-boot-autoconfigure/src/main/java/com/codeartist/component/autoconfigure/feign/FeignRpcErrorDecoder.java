package com.codeartist.component.autoconfigure.feign;

import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.entity.enums.ApiHttpStatus;
import com.codeartist.component.core.exception.FeignException;
import com.codeartist.component.core.util.JSON;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;

/**
 * Feign监控异常处理
 *
 * @author AiJiangnan
 * @date 2021/7/23
 */
public class FeignRpcErrorDecoder extends ErrorDecoder.Default {

    private final static Logger log = LoggerFactory.getLogger(FeignRpcErrorDecoder.class);

    /**
     * 当Feign请求异常时，这里只考虑内部服务的Feign调用，不适用于对接第三方调用
     * <p>
     * 请求异常和业务异常会进行异常转换，其他异常会走原有流程，重试或者熔断等操作
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        if (ApiHttpStatus.isWarning(response.status()) || ApiHttpStatus.isError(response.status())) {
            try {
                byte[] body = StreamUtils.copyToByteArray(response.body().asInputStream());
                ErrorResp errorResp = JSON.parseObject(body, ErrorResp.class);
                return new FeignException(methodKey, errorResp);
            } catch (IOException e) {
                log.error("Feign exception convert error.", e);
                return super.decode(methodKey, response);
            }
        }
        return super.decode(methodKey, response);
    }
}
