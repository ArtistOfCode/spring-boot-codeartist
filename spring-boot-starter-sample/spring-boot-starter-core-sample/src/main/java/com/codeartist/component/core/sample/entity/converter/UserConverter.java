package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.param.UserParam;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import com.codeartist.component.core.support.curd.BaseConverter;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * 用户基本信息 实体转换
 *
 * @author CodeGenerator
 * @since 2023-06-29
 */
@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserConverter extends BaseConverter<User, UserParam, UserVO> {
}
