package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.PageInfo;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.event.EntityEvent;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.auth.AuthContext;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

/**
 * 抽象服务类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
public abstract class AbstractService<D, R, P extends PageParam> implements BaseService<R, P> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Getter
    @Autowired
    private BaseMapper<D> mapper;
    @Getter
    @Autowired
    private BaseConverter<D, P, R> converter;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private ObjectProvider<EntityChecker<P, D>> entityCheckers;
    @Autowired
    private ObjectProvider<EntityConsumer<P, D>> entityContextConsumers;
    @Autowired
    private ObjectProvider<TransactionTemplate> transactionTemplate;

    @Override
    public R get(Long id) {
        Assert.notNull(id, "ID不能为空");
        D entity = getMapper().selectById(id);
        return getConverter().toVo(entity);
    }

    @Override
    public PageInfo<R> get(P p) {
        D entity = getConverter().toDo(p);

        QueryWrapper<D> wrapper = Wrappers.query(entity)
                .orderBy(p.getOrderBy() != null, p.getAsc(), p.getOrderBy());

        IPage<D> page = getMapper().selectPage(p.page(), wrapper);
        return new PageInfo<>(page, getConverter()::toVo);
    }

    @Override
    public void save(P p) {
        basicCheck(p);

        EntityContext<P, D> context = createContext(p.getId() == null ? "Save context" : "Update context");
        context.setParam(p);

        // 带有事务的业务
        getTransactionTemplate().executeWithoutResult(status -> {
            if (p.getId() == null) {
                doSave(p, context);
            } else {
                doUpdate(p, context);
            }
            doFinally(context);
        });
    }

    @Override
    public void delete(Long id) {
        D old = getMapper().selectById(id);
        if (old == null) {
            return;
        }

        EntityContext<P, D> context = createContext("Delete context");
        context.setDelete(true);
        context.setEntity(old);

        // 带有事务的业务
        getTransactionTemplate().executeWithoutResult(status -> {
            businessCheck(context);

            preConsumer(context);
            getMapper().deleteById(id);
            postConsumer(context);

            SpringContext.publishEvent(new EntityEvent<>(this, context));

            doFinally(context);
        });
    }

    private void doSave(P p, EntityContext<P, D> context) {
        Long userId = authContext.getUserId();
        context.setSave(true);

        p.setCreateUser(userId);
        p.setUpdateUser(userId);

        businessCheck(context);

        D entity = getConverter().toDo(p);
        context.setEntity(entity);

        preConsumer(context);
        getMapper().insert(entity);
        postConsumer(context);

        SpringContext.publishEvent(new EntityEvent<>(this, context));
    }

    private void doUpdate(P p, EntityContext<P, D> context) {
        Long userId = authContext.getUserId();
        context.setUpdate(true);

        D old = getMapper().selectById(p.getId());
        context.setOldEntity(old);

        if (old == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }
        p.setUpdateUser(userId);

        businessCheck(context);

        D entity = getConverter().toDo(p);
        context.setEntity(entity);
        preConsumer(context);
        getMapper().updateById(entity);
        postConsumer(context);

        SpringContext.publishEvent(new EntityEvent<>(this, context));
    }

    /**
     * 创建上下文
     */
    protected EntityContext<P, D> createContext(String id) {
        return new DefaultEntityContext<>(id);
    }

    /**
     * 获取事务操作接口（只允许一个事务Bean存在）
     */
    protected TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate.getIfUnique();
    }

    /**
     * 参数基础校验
     */
    protected void basicCheck(P param) {
        SpringContext.validate(param);
    }

    /**
     * 业务校验
     */
    private void businessCheck(EntityContext<P, D> context) {
        entityCheckers.stream().forEach(checker -> checker.check(context));
    }

    /**
     * 执行前置处理
     */
    private void preConsumer(EntityContext<P, D> context) {
        entityContextConsumers.stream().forEach(consumer -> consumer.preConsumer(context));
    }

    /**
     * 执行后置处理
     */
    private void postConsumer(EntityContext<P, D> context) {
        entityContextConsumers.stream().forEach(consumer -> consumer.postConsumer(context));
    }

    /**
     * 执行最终处理
     */
    private void doFinally(EntityContext<P, D> context) {
        StopWatch stopWatch = context.getStopWatch();
        if (stopWatch != null && stopWatch.getTaskCount() > 0) {
            log.debug("\n{}", stopWatch.prettyPrint());
        }
        context.clear();
    }
}