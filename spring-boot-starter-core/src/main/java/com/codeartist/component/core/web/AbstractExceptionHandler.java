package com.codeartist.component.core.web;


import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.ErrorResp;
import com.codeartist.component.core.support.message.I18nMessageSource;
import com.codeartist.component.core.support.metric.Metrics;
import com.codeartist.component.core.support.props.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常处理抽象类
 *
 * @author AiJiangnan
 * @date 2025/7/11
 */
public abstract class AbstractExceptionHandler {

    @Autowired
    protected AppProperties appProperties;
    @Autowired
    protected Metrics metrics;

    protected ErrorResp parseErrors(I18nMessageSource i18nMessageSource, Errors errors) {
        String message = SpringContext.getMessage(i18nMessageSource);
        List<String> globalErrors = Collections.emptyList();
        Map<String, String> fieldErrors = Collections.emptyMap();

        if (errors.hasFieldErrors()) {
            fieldErrors = errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, SpringContext::getMessage, (k1, k2) -> k1));
            for (String key : fieldErrors.keySet()) {
                message = key + ":" + SpringContext.getMessage(fieldErrors.get(key));
                break;
            }
        }
        if (errors.hasGlobalErrors()) {
            globalErrors = errors.getGlobalErrors().stream().map(SpringContext::getMessage).collect(Collectors.toList());
            message = SpringContext.getMessage(errors.getGlobalError());
        }
        return ErrorResp.builder()
                .service(appProperties.getName())
                .code(i18nMessageSource.name())
                .message(message)
                .errors(globalErrors)
                .fieldErrors(fieldErrors)
                .build();
    }
}
