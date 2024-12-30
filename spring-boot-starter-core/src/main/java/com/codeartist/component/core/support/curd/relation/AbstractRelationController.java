package com.codeartist.component.core.support.curd.relation;

import com.codeartist.component.core.SpringContext;
import com.codeartist.component.core.entity.Relation;
import com.codeartist.component.core.entity.param.UpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 抽象控制类
 *
 * @param <D> 响应实体对象类型
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractRelationController<D, R extends Relation<D>> {

    @Autowired
    private AbstractRelationService<D> service;

    @GetMapping
    @Operation(summary = "查询接口")
    public Relation<D> get(R param) {
        return getService().get(param);
    }

    @PutMapping
    @Operation(summary = "更新接口")
    public void save(@RequestBody R param) {
        SpringContext.validate(param, UpdateParam.class);
        getService().save(param);
    }

    @DeleteMapping
    @Operation(summary = "删除接口")
    public void delete(R param) {
        getService().delete(param);
    }
}
