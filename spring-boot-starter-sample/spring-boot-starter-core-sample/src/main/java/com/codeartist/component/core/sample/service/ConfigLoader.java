package com.codeartist.component.core.sample.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codeartist.component.core.sample.entity.Config;
import com.codeartist.component.core.sample.entity.converter.ConfigConverter;
import com.codeartist.component.core.sample.entity.vo.ConfigVO;
import com.codeartist.component.core.sample.mapper.ConfigMapper;
import com.codeartist.component.core.support.props.DefaultLocalPropertyLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置加载
 *
 * @author AiJiangnan
 * @date 2025/7/9
 */
@Slf4j
@Component
public class ConfigLoader extends DefaultLocalPropertyLoader {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ConfigConverter converter;

    @Override
    protected String load(String key) {
        Config config = configMapper.selectOne(Wrappers.<Config>lambdaQuery().eq(Config::getName, key));
        if (config == null) {
            log.warn("config item is not exist, please check it. key: {}", key);
            return null;
        }
        return config.getConfigValue();
    }

    @Override
    protected List<ConfigVO> loadAll() {
        return converter.toVo(configMapper.selectList(Wrappers.emptyWrapper()));
    }
}