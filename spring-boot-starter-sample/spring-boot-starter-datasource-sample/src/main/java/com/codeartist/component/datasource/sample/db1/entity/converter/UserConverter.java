package com.codeartist.component.datasource.sample.db1.entity.converter;

import com.codeartist.component.core.support.curd.BaseConverter;
import com.codeartist.component.datasource.sample.db1.entity.User;
import com.codeartist.component.datasource.sample.db1.entity.param.UserParam;
import com.codeartist.component.datasource.sample.db1.entity.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * 用户基本信息 实体转换
 *
 * @author CodeGenerator
 * @since 2024-08-02
 */
@Mapper(componentModel = "spring")
public interface UserConverter extends BaseConverter<User, UserParam, UserVO> {
}
