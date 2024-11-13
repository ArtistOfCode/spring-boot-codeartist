package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.support.curd.BaseConverter;
import com.codeartist.component.core.sample.entity.UserRole;
import com.codeartist.component.core.sample.entity.param.UserRoleParam;
import com.codeartist.component.core.sample.entity.vo.UserRoleVO;
import org.mapstruct.Mapper;

/**
 * 用户角色关联信息 实体转换
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Mapper(componentModel = "spring")
public interface UserRoleConverter extends BaseConverter<UserRole, UserRoleParam, UserRoleVO> {
}
