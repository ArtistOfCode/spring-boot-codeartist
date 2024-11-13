package com.codeartist.component.autoconfigure.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.codeartist.component.core.support.serializer.JacksonSerializer;
import com.codeartist.component.core.support.uuid.DefaultIdGenerator;
import com.codeartist.component.core.support.uuid.IdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * MyBatis自动配置
 *
 * @author AiJiangnan
 * @date 2022/9/2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({MybatisPlusInterceptor.class, JacksonTypeHandler.class})
public class MyBatisAutoConfiguration {

    @PostConstruct
    public void init() {
        // MyBatisPlus类型处理器默认的JSON序列化配置
        JacksonTypeHandler.setObjectMapper(JacksonSerializer.simpleMapper());
    }

    /**
     * MyBatisPlus拦截器配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 分布式ID生成器，默认实现，后续可以考虑单独封装ID生成器
     * <p>
     * TODO 重新优化
     */
    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }
}
