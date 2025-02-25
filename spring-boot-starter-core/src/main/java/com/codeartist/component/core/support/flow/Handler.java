package com.codeartist.component.core.support.flow;


import org.springframework.beans.factory.BeanNameAware;

/**
 * 处理器接口
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
public interface Handler extends BeanNameAware {

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
        return true;
    }
}
