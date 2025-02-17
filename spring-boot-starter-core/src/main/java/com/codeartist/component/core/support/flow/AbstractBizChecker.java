package com.codeartist.component.core.support.flow;


import lombok.Getter;
import lombok.Setter;

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
}
