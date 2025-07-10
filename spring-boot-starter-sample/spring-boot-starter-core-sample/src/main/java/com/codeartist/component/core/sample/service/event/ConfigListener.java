package com.codeartist.component.core.sample.service.event;


import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.sample.entity.Config;
import com.codeartist.component.core.sample.entity.converter.ConfigConverter;
import com.codeartist.component.core.support.curd.EntityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 新增配置事件
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
@Component
public class ConfigListener implements ApplicationListener<EntityEvent<Config>> {

    @Autowired
    private ConfigConverter converter;

    @Override
    public void onApplicationEvent(EntityEvent<Config> event) {
        Config entity = event.getEntityContext().getEntity();
        if (event.isDelete()) {
            entity.setConfigValue(null);
        }
        SpringContext.publishEvent(converter.toVo(entity));
    }
}
