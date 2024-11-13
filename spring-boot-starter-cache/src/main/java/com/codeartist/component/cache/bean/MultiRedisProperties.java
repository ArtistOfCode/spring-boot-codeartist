package com.codeartist.component.cache.bean;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author AiJiangnan
 * @date 2023/7/20
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class MultiRedisProperties {

    /**
     * 多数据源配置，Key为数据源名称
     */
    private Map<String, RedisProperties> multi;
}
