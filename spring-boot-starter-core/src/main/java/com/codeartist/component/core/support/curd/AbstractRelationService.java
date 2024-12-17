package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codeartist.component.core.entity.Relation;
import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽象关联服务类
 *
 * @author AiJiangnan
 * @date 2023-12-10
 */
@Getter
public abstract class AbstractRelationService<D> implements RelationService<D> {

    @Autowired
    private BaseMapper<D> mapper;
    @Autowired
    private ObjectProvider<TransactionTemplate> transactionTemplate;

    @Override
    public Relation get(Long id, Function<D, Long> field) {
        List<D> entityList = getMapper().selectList(Wrappers.<D>lambdaQuery().eq(field::apply, id));
        if (CollectionUtils.isEmpty(entityList)) {
            return new Relation(id, Collections.emptySet());
        }
        Set<Long> ids = entityList.stream().map(field).collect(Collectors.toSet());
        return new Relation(id, ids);
    }

    @Override
    public void save(List<D> param, Function<D, Long> field) {
        if (CollectionUtils.isEmpty(param)) {
            return;
        }

        Long id = field.apply(param.get(0));

        getTransactionTemplate().executeWithoutResult(status -> {
            delete(id, field);
            getMapper().insert(param);
        });
    }

    @Override
    public void delete(Long id, Function<D, Long> field) {
        getMapper().delete(Wrappers.<D>lambdaQuery().eq(field::apply, id));
    }

    /**
     * 获取事务操作接口（只允许一个事务Bean存在）
     */
    protected TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate.getIfUnique();
    }
}
