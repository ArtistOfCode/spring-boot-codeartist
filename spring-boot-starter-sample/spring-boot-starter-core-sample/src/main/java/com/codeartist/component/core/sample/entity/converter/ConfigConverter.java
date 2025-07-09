package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.sample.entity.Config;
import com.codeartist.component.core.sample.entity.param.ConfigParam;
import com.codeartist.component.core.sample.entity.vo.ConfigVO;
import com.codeartist.component.core.support.curd.BaseConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 系统配置信息 实体转换
 *
 * @author CodeGenerator
 * @since 2025-07-09
 */
@Mapper(componentModel = "spring")
public interface ConfigConverter extends BaseConverter<Config, ConfigParam, ConfigVO> {

    @Mapping(target = "key", source = "name")
    @Mapping(target = "value", source = "configValue")
    @Override
    ConfigVO toVo(Config param);
}
