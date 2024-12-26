package com.codeartist.component.autoconfigure.cache;

import com.codeartist.component.cache.CaffeineAutoConfiguration;
import com.codeartist.component.cache.RedisAutoConfiguration;
import com.codeartist.component.cache.bean.CacheProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存组件
 *
 * @author AiJiangnan
 * @date 2019/4/19
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CacheProperties.class)
@EnableConfigurationProperties(CacheProperties.class)
@Import({CaffeineAutoConfiguration.class, RedisAutoConfiguration.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class CacheAutoConfiguration {

}
