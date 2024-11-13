package com.codeartist.component.datasource.sample.db1.service;

import com.codeartist.component.core.support.curd.AbstractService;
import com.codeartist.component.datasource.sample.db1.entity.User;
import com.codeartist.component.datasource.sample.db1.entity.param.UserParam;
import com.codeartist.component.datasource.sample.db1.entity.vo.UserVO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 用户基本信息 服务实现类
 *
 * @author CodeGenerator
 * @since 2024-08-02
 */
@Getter
@Service
public class UserService extends AbstractService<User, UserVO, UserParam> {

    @Autowired
    private TransactionTemplate db1TransactionTemplate;

    public void insert() {
        User user = new User();
        user.setName("Test1");
        user.setUsername("Test1");
        user.setPassword("Test1");
        getMapper().insert(user);

        user = new User();
        user.setName("Test2");
        user.setUsername("Test2");
        getMapper().insert(user);
    }
}
