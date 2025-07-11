package com.codeartist.component.core.support.flow;


import com.codeartist.component.core.support.message.I18nMessageSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * 业务检查抽象类
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
@Getter
@Setter
public abstract class AbstractBizChecker<P, C extends Context<P>> implements BizChecker<P, C> {

    private String beanName;
    private Errors errors = new BeanPropertyBindingResult(null, "context");

    protected void reject(String errorCode, String defaultMessage) {
        errors.reject(errorCode, defaultMessage);
    }

    protected void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
        errors.reject(errorCode, errorArgs, defaultMessage);
    }

    protected void reject(I18nMessageSource i18nMessageSource) {
        errors.reject(i18nMessageSource.getCode(), i18nMessageSource.getArguments(), i18nMessageSource.getDefaultMessage());
    }

    protected void rejectValue(String field, String errorCode) {
        errors.rejectValue(field, errorCode);
    }

    protected void rejectValue(String field, String errorCode, String defaultMessage) {
        errors.rejectValue(field, errorCode, defaultMessage);
    }

    protected void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
        errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
    }

    protected void rejectValue(String field, I18nMessageSource i18nMessageSource) {
        errors.rejectValue(field, i18nMessageSource.getCode(), i18nMessageSource.getArguments(), i18nMessageSource.getDefaultMessage());
    }
}
