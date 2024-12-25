package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codeartist.component.core.entity.PageInfo;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.curd.EntityConsumer.EntityChecker;
import com.codeartist.component.core.support.curd.EntityConsumer.PostEntityConsumer;
import com.codeartist.component.core.support.curd.EntityConsumer.PreEntityConsumer;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象服务类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractService<D, R, P extends PageParam> implements BaseService<R, P> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseMapper<D> mapper;
    @Autowired
    private BaseConverter<D, P, R> converter;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private ObjectProvider<EntityChecker<P, D>> entityCheckers;
    @Autowired
    private ObjectProvider<PreEntityConsumer<P, D>> preEntityConsumers;
    @Autowired
    private ObjectProvider<PostEntityConsumer<P, D>> postEntityConsumers;

    @Override
    public R get(Long id) {
        D entity = getMapper().selectById(id);
        return getConverter().toVo(entity);
    }

    @Override
    public PageInfo<R> get(P param) {
        D entity = getConverter().toDo(param);

        QueryWrapper<D> wrapper = Wrappers.query(entity)
                .orderBy(param.getOrderBy() != null, param.getAsc(), param.getOrderBy());

        IPage<D> page = getMapper().selectPage(param.page(), wrapper);
        return new PageInfo<>(page, getConverter());
    }

    @Override
    public void save(P param) {
        new SaveEntityHandler().apply(param);
    }

    @Override
    public void update(P param) {
        new UpdateEntityHandler().apply(param);
    }

    @Override
    public void delete(Long id) {
        new DeleteEntityHandler(id).apply(null);
    }

    private class SaveEntityHandler extends AbstractEntityHandler<P, D> {

        public SaveEntityHandler() {
            super(EntityAction.SAVE, AbstractService.this);
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            getMapper().insert(context.getEntity());
        }
    }

    private class UpdateEntityHandler extends AbstractEntityHandler<P, D> {

        public UpdateEntityHandler() {
            super(EntityAction.UPDATE, AbstractService.this);
        }

        @Override
        public void basicCheck(P param) {
            if (param.getId() == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
            }
            super.basicCheck(param);
        }

        @Override
        public EntityContext<P, D> createContext(P param) {
            DefaultEntityContext<P, D> context = (DefaultEntityContext<P, D>) super.createContext(param);
            D old = getMapper().selectById(param.getId());
            context.setOldEntity(old);
            if (old == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
            }
            return context;
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            getMapper().updateById(context.getEntity());
        }
    }

    private class DeleteEntityHandler extends AbstractEntityHandler<P, D> {

        private final Long id;

        public DeleteEntityHandler(Long id) {
            super(EntityAction.DELETE, AbstractService.this);
            this.id = id;
        }

        @Override
        public void basicCheck(P param) {
        }

        @Override
        public EntityContext<P, D> createContext(P param) {
            D old = getMapper().selectById(id);
            if (old == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
            }

            DefaultEntityContext<P, D> context = (DefaultEntityContext<P, D>) super.createContext(param);
            context.setEntity(old);
            context.setOldEntity(old);
            return context;
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            getMapper().deleteById(id);
        }
    }
}
