package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.sample.entity.Role;
import com.codeartist.component.core.sample.entity.param.RoleParam;
import com.codeartist.component.core.sample.entity.vo.RoleVO;
import com.codeartist.component.core.support.curd.BaseConverter;
import org.mapstruct.Mapper;

/**
 * 角色信息 实体转换
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Mapper(componentModel = "spring")
public interface RoleConverter extends BaseConverter<Role, RoleParam, RoleVO> {
}
