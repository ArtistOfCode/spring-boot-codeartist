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
import com.codeartist.component.core.support.flow.AbstractHandler;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象服务类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractService<D, R, P extends PageParam>
        extends AbstractHandler<P, R, EntityContext<P, D>> implements BaseService<R, P> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseMapper<D> mapper;
    @Autowired
    private BaseConverter<D, P, R> converter;
    @Autowired
    private AuthContext authContext;

    //region Service

    @Override
    public R get(Long id) {
        return new GetEntityHandler(id).apply(null);
    }

    @Override
    public PageInfo<R> get(P param) {
        return new PageEntityHandler().apply(param);
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

    //endregion

    //region Handler

    @Override
    public EntityContext<P, D> createContext(P param) {
        DefaultEntityContext<P, D, R> context = new DefaultEntityContext<>();
        context.setParam(param);
        D entity = getConverter().toDo(param);
        context.setEntity(entity);
        return context;
    }

    @Override
    public void execute(EntityContext<P, D> context) {
        throw new UnsupportedOperationException();
    }

    //endregion

    private class GetEntityHandler extends AbstractEntityHandler<P, D, R> {

        private final Long id;

        private GetEntityHandler(Long id) {
            super(EntityAction.GET, AbstractService.this);
            this.id = id;
        }

        @Override
        public void basicCheck(P param) {
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            D entity = getMapper().selectById(id);
            ((DefaultEntityContext<P, D, R>) context).setEntity(entity);
            R result = getConverter().toVo(entity);
            ((DefaultEntityContext<P, D, R>) context).setResult(result);
        }

        @Override
        public void publishEvent(EntityContext<P, D> context) {
        }
    }

    private class PageEntityHandler extends AbstractEntityHandler<P, D, PageInfo<R>> {

        public PageEntityHandler() {
            super(EntityAction.QUERY, AbstractService.this);
        }

        @Override
        public void basicCheck(P param) {
        }

        @Override
        public EntityContext<P, D> createContext(P param) {
            DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) super.createContext(param);
            context.setAction(EntityAction.QUERY);
            return context;
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            P param = context.getParam();
            D entity = getConverter().toDo(param);

            QueryWrapper<D> wrapper = Wrappers.query(entity)
                    .orderBy(param.getOrderBy() != null, param.getAsc(), param.getOrderBy());

            IPage<D> page = getMapper().selectPage(param.page(), wrapper);
            PageInfo<R> pageInfo = new PageInfo<>(page, getConverter());
            ((DefaultEntityContext<P, D, PageInfo<R>>) context).setResult(pageInfo);
        }

        @Override
        public void publishEvent(EntityContext<P, D> context) {
        }
    }

    private class SaveEntityHandler extends AbstractEntityHandler<P, D, R> {

        public SaveEntityHandler() {
            super(EntityAction.SAVE, AbstractService.this);
        }

        @Override
        public EntityContext<P, D> createContext(P param) {
            EntityContext<P, D> context = super.createContext(param);
            Long userId = authContext.getUserId();
            param.setCreateUser(userId);
            param.setUpdateUser(userId);
            return context;
        }

        @Override
        public void execute(EntityContext<P, D> context) {
            getMapper().insert(context.getEntity());
        }
    }

    private class UpdateEntityHandler extends AbstractEntityHandler<P, D, R> {

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
            DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) super.createContext(param);
            Long userId = authContext.getUserId();
            param.setUpdateUser(userId);

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

    private class DeleteEntityHandler extends AbstractEntityHandler<P, D, R> {

        private final Long id;

        public DeleteEntityHandler(Long id) {
            super(EntityAction.DELETE, AbstractService.this);
            this.id = id;
        }

        @Override
        public void basicCheck(P param) {
            if (id == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
            }
        }

        @Override
        public EntityContext<P, D> createContext(P param) {
            D old = getMapper().selectById(id);
            if (old == null) {
                throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
            }

            DefaultEntityContext<P, D, R> context = (DefaultEntityContext<P, D, R>) super.createContext(param);
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
