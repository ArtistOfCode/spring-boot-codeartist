package com.codeartist.component.core.support.flow;


import lombok.Getter;
import lombok.Setter;

/**
 * 业务扩展处理抽象类
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
@Getter
@Setter
public class AbstractBizConsumer<P, C extends Context<P>> implements BizConsumer<P, C> {

    private String beanName;
}
