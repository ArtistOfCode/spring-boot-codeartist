package com.codeartist.component.core.support.curd.relation;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codeartist.component.core.entity.Relation;
import com.codeartist.component.core.entity.enums.GlobalErrorCode;
import com.codeartist.component.core.exception.BadRequestException;
import com.codeartist.component.core.support.curd.BaseConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象关联服务类
 *
 * @author AiJiangnan
 * @date 2023-12-10
 */
@Getter
public abstract class AbstractRelationService<D> implements RelationService {

    @Autowired
    private BaseConverter<D, ?, ?> converter;
    @Autowired
    private RelationMapper<D> mapper;

    @Override
    public Relation get(Relation param) {
        Long id = param.getId();
        if (id == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }

        List<Map<String, Object>> entityList = getMapper().selectMaps(Wrappers.<D>query().eq(param.getOne(), id));

        if (CollectionUtils.isEmpty(entityList)) {
            param.setIds(Collections.emptySet());
        } else {
            param.setIds(entityList.stream().map(m -> (Long) m.get(param.getMore())).collect(Collectors.toSet()));
        }

        return param;
    }

    @Override
    public void save(Relation param) {
        if (param.getId() == null || CollectionUtils.isEmpty(param.getIds())) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }

        delete(param);
        List<D> entity = param.getIds().stream()
                .map(id -> param.isInvert() ?
                        converter.toInvertRel(param.getId(), id) : converter.toRel(param.getId(), id))
                .collect(Collectors.toList());
        getMapper().insert(entity);
    }

    @Override
    public void delete(Relation param) {
        if (param.getId() == null) {
            throw new BadRequestException(GlobalErrorCode.GLOBAL_DATA_NULL_ERROR);
        }

        getMapper().delete(Wrappers.<D>query().eq(param.getOne(), param.getId()));
    }
}
