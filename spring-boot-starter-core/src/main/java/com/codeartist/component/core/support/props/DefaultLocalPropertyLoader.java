package com.codeartist.component.core.support.props;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

/**
 * 本地缓存配置默认实现
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
@Slf4j
@Getter
public abstract class DefaultLocalPropertyLoader implements LocalPropertyLoader {

    private final LoadingCache<String, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .maximumSize(1000)
            .build(this::load);

    protected abstract String load(String key);

    protected abstract List<? extends LocalConfig> loadAll();

    @PostConstruct
    @Scheduled(cron = "${spring.caffeine.config.refresh.cron:*/10 * * * * ?}")
    public void schedule() {
        List<? extends LocalConfig> configs = loadAll();
        if (CollectionUtils.isEmpty(configs)) {
            log.warn("refresh config items is empty, please check it.");
            return;
        }
        configs.forEach(config -> getCache().put(config.getKey(), config.getValue()));
        log.debug("refresh config for items: {}", configs.size());
    }
}
