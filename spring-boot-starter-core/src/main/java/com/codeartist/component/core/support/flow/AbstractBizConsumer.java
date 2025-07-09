package com.codeartist.component.core.support.flow;


import lombok.Getter;
import lombok.Setter;

/**
 * 业务扩展处理抽象类
 *
 * @author AiJiangnan
 * @date 2025/2/17
 */
public class AbstractBizConsumer {

    @Getter
    @Setter
    public static abstract class Pre<P, C extends Context<P>> implements BizConsumer.Pre<P, C> {
        private String beanName;
    }

    @Getter
    @Setter
    public static abstract class Post<P, C extends Context<P>> implements BizConsumer.Post<P, C> {
        private String beanName;
    }
}
