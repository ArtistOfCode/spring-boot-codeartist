package com.codeartist.component.cache.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Redis多数据源配置
 *
 * @author AiJiangnan
 * @date 2023/7/20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis")
public class MultiRedisProperties {

    /**
     * 多数据源配置，Key为数据源名称
     */
    private Map<String, RedisProperties> multi;
}
