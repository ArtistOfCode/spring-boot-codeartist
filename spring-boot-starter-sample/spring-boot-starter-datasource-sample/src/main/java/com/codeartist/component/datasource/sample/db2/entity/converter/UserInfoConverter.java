package com.codeartist.component.datasource.sample.db2.entity.converter;

import com.codeartist.component.core.support.curd.BaseConverter;
import com.codeartist.component.datasource.sample.db2.entity.UserInfo;
import com.codeartist.component.datasource.sample.db2.entity.param.UserInfoParam;
import com.codeartist.component.datasource.sample.db2.entity.vo.UserInfoVO;
import org.mapstruct.Mapper;

/**
 * 用户基本信息 实体转换
 *
 * @author CodeGenerator
 * @since 2024-08-02
 */
@Mapper(componentModel = "spring")
public interface UserInfoConverter extends BaseConverter<UserInfo, UserInfoParam, UserInfoVO> {
}
