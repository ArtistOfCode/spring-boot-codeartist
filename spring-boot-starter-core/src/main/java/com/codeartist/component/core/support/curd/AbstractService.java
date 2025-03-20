package com.codeartist.component.core.support.curd;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeartist.component.core.entity.PageInfo;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.entity.param.PageParam;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.auth.AuthContext;
import com.codeartist.component.core.support.curd.handler.DeleteEntityHandler;
import com.codeartist.component.core.support.curd.handler.SaveEntityHandler;
import com.codeartist.component.core.support.curd.handler.UpdateEntityHandler;
import com.codeartist.component.core.support.flow.AbstractHandler;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 抽象服务类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractService<P extends PageParam, D, R>
        extends AbstractHandler<P, R, EntityContext<P, D>> implements BaseService<P, R> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseMapper<D> mapper;
    @Autowired
    private BaseConverter<D, P, R> converter;
    @Autowired
    private AuthContext authContext;

    @Override
    public R get(Long id) {
        if (id == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }
        D entity = getMapper().selectById(id);
        return getConverter().toVo(entity);
    }

    @Override
    public List<R> list(P param) {
        D entity = getConverter().toDo(param);

        QueryWrapper<D> wrapper = Wrappers.query(entity)
                .orderBy(param.getOrderBy() != null, param.getAsc(), param.getOrderBy());

        Page<D> iPage = param.page();
        iPage.setSearchCount(false);

        IPage<D> page = getMapper().selectPage(iPage, wrapper);
        return new PageInfo<>(page, getConverter()).getRecords();
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
        new SaveEntityHandler<>(this).apply(param);
    }

    @Override
    public void update(P param) {
        new UpdateEntityHandler<>(this).apply(param);
    }

    @Override
    public void delete(Long id) {
        new DeleteEntityHandler<>(id, this).apply(null);
    }

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
}
