package com.codeartist.component.autoconfigure.metrics;

import com.codeartist.component.core.support.metric.DefaultMetrics;
import com.codeartist.component.core.support.metric.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 指标组件
 *
 * @author AiJiangnan
 * @date 2022/7/15
 */
@Configuration(proxyBeanMethods = false)
@Import({MetricsRegistryAutoConfiguration.class, MetricAutoConfiguration.WebMetricAutoConfiguration.class})
public class MetricAutoConfiguration {

    /**
     * 默认使用指标收集
     */
    @Bean
    @ConditionalOnMissingBean(Metrics.class)
    public Metrics defaultMetrics() {
        return new DefaultMetrics();
    }

    @ConditionalOnClass(WebMvcTagsProvider.class)
    static class WebMetricAutoConfiguration {
        @Bean
        public WebMvcTagsProvider webMvcTagsProvider() {
            return new DefaultWebMvcTagsProvider() {
                @Override
                public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
                    return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response), WebMvcTags.status(response));
                }
            };
        }
    }
}
