package com.codeartist.component.core.sample.entity.converter;

import com.codeartist.component.core.sample.entity.User;
import com.codeartist.component.core.sample.entity.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserMapperDecorator implements UserConverter {

    @Autowired
    @Qualifier("delegate")
    private UserConverter delegate;

    @Override
    public UserVO toVo(User param) {
        UserVO vo = delegate.toVo(param);
        vo.setName("Aijiangnan");
        return vo;
    }
}
