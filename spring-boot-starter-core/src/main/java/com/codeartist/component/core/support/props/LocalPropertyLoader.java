package com.codeartist.component.core.support.props;


import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

/**
 * 本地配置加载
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
@FunctionalInterface
public interface LocalPropertyLoader extends ApplicationListener<PayloadApplicationEvent<? extends LocalConfig>> {

    LoadingCache<String, String> getCache();

    /**
     * 通过Spring事件来通知更新单个Key的缓存配置
     */
    default void onApplicationEvent(PayloadApplicationEvent<? extends LocalConfig> event) {
        LocalConfig config = event.getPayload();
        if (config.getValue() == null) {
            // 如果事件中的Value为空，则通过load刷新缓存
            getCache().refresh(config.getKey());
        } else {
            // 如果事件中的Value不为空，则更新缓存
            getCache().put(config.getKey(), config.getValue());
        }
    }
}
