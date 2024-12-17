package com.codeartist.component.core.support.business;

/**
 * 基础服务类
 *
 * @author AiJiangnan
 * @since 2022-08-31
 */
@FunctionalInterface
public interface BaseHandler<P, R> {

    R execute(P param);
}
