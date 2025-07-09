package com.codeartist.component.core.support.props;


import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 * 本地缓存配置集成到Spring配置中
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
public class LocalCachePropertySource extends PropertySource<LoadingCache<String, String>>
        implements EnvironmentAware, CommandLineRunner {

    private ConfigurableEnvironment environment;

    public LocalCachePropertySource(LoadingCache<String, String> cache) {
        super("LocalCachePropertySource", cache);
    }

    @Override
    public Object getProperty(String name) {
        // 使用get方法读取缓存数据，如果缓存不存在，触发load加载数据并缓存
        return getSource().get(name);
    }

    @Override
    public void run(String... args) {
        environment.getPropertySources().addLast(this);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
