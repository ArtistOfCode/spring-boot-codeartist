package com.codeartist.component.cache.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author AiJiangnan
 * @date 2021/5/24
 */
@Data
@ConfigurationProperties("spring.cache")
public class CacheProperties {

    public static final String DELIMITER = ":";

    private Duration nullTimeout = Duration.ofMinutes(2);

    private Caffeine caffeine = new Caffeine();

    private Redis redis = new Redis();

    @Data
    public static class Caffeine {
    }

    @Data
    public static class Redis {
    }
}
