package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.sample.entity.UserRole;
import com.codeartist.component.core.sample.entity.param.UserRoleParam;
import com.codeartist.component.core.sample.entity.vo.UserRoleVO;
import com.codeartist.component.core.support.curd.BaseConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户角色关联信息 实体转换
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Mapper(componentModel = "spring")
public interface UserRoleConverter extends BaseConverter<UserRole, UserRoleParam, UserRoleVO> {

    @Mapping(target = "id", ignore = true)
    UserRole toRel(Long userId, Long roleId);

    @Mapping(target = "id", ignore = true)
    UserRole toInvertRel(Long roleId, Long userId);
}
