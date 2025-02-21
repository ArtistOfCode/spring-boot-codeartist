package com.codeartist.component.cache.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 缓存配置
 *
 * @author AiJiangnan
 * @date 2021/5/24
 */
@Getter
@Setter
@ConfigurationProperties("spring.cache")
public class CacheProperties {

    private Duration nullTimeout = Duration.ofMinutes(2);

    private Caffeine caffeine = new Caffeine() {{
        setMaxSize(1000);
    }};

    private Redis redis = new Redis();

    @Getter
    @Setter
    public static class Caffeine {
        private int maxSize;
    }

    @Getter
    @Setter
    public static class Redis {
    }
}
