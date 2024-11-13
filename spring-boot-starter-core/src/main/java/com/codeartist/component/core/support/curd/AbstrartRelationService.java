package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.Relation;
import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象关联服务类
 *
 * @author AiJiangnan
 * @date 2023-12-10
 */
public abstract class AbstrartRelationService<D> implements RelationService {

    @Getter
    @Autowired
    private RelationMapper<D> mapper;
    @Autowired
    private ObjectProvider<TransactionTemplate> transactionTemplate;

    private final SFunction<D, Long> one;
    private final SFunction<D, Long> more;

    protected AbstrartRelationService(SFunction<D, Long> one, SFunction<D, Long> more) {
        this.one = one;
        this.more = more;
    }

    @Override
    public Relation get(Long id, boolean column) {
        Set<Long> ids = getMapper().selectObjs(Wrappers.<D>lambdaQuery()
                        .eq(column, one, id)
                        .eq(!column, more, id)
                        .select(column ? more : one))
                .stream().map(o -> (Long) o).collect(Collectors.toSet());

        Relation entity = new Relation();
        entity.setId(id);
        entity.setIds(ids);
        return entity;
    }

    @Override
    public void save(Relation param, boolean column) {
        basicCheck(param);

        getTransactionTemplate().executeWithoutResult(status -> {
            Relation origin = get(param.getId(), column);
            Set<Long> originIds = origin.getIds();
            Set<Long> originIdsCopy = new HashSet<>(originIds);

            originIds.removeAll(param.getIds());
            param.getIds().removeAll(originIdsCopy);

            delete(param.getId(), originIds, column);
            getMapper().insertBatch(param, column);
        });
    }

    @Override
    public void delete(Long id, boolean column) {
        getMapper().delete(Wrappers.<D>lambdaQuery()
                .eq(column, one, id)
                .eq(!column, more, id));
    }

    private void delete(Long id, Set<Long> ids, boolean column) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        getMapper().delete(Wrappers.<D>lambdaQuery()
                .eq(column, one, id)
                .in(column, more, ids)
                .eq(!column, more, id)
                .in(!column, one, ids));
    }

    /**
     * 获取事务操作接口（只允许一个事务Bean存在）
     */
    protected TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate.getIfUnique();
    }

    private void basicCheck(Relation param) {
        SpringContext.validate(param);
    }
}
