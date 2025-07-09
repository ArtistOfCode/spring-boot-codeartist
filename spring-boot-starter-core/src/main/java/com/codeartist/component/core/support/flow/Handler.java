package com.codeartist.component.core.support.flow;


import com.codeartist.component.core.SpringContext;
import org.springframework.beans.factory.BeanNameAware;

import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * 处理器接口
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
public interface Handler<C> extends Consumer<C>, BeanNameAware {

    String SPRING_HANDLER_ENABLE = "spring.handler.%s.enable";

    /**
     * 处理器Bean名称
     */
    String getBeanName();

    /**
     * 获取可操作类型
     */
    default Enum<?>[] getAction() {
        return new BizAction[]{BizAction.DEFAULT};
    }

    /**
     * 是否启用（默认：启用）
     */
    default boolean isEnabled() {
        return SpringContext.getProperty(format(SPRING_HANDLER_ENABLE, getBeanName()), Boolean.class, true);
    }
}
