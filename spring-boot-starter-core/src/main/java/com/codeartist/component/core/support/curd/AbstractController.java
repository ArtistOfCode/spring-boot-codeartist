package com.codeartist.component.core.support.curd;

import com.codeartist.component.core.entity.PageInfo;
import com.codeartist.component.core.entity.param.PageParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 抽象控制类
 *
 * @author AiJiangnan
 * @date 2023/6/1
 */
@Getter
public abstract class AbstractController<R, P extends PageParam> {

    @Autowired
    private BaseService<R, P> service;

    @GetMapping("/{id}")
    @Operation(summary = "详情接口")
    public R get(@PathVariable("id") Long id) {
        return getService().get(id);
    }

    @GetMapping
    @Operation(summary = "查询接口")
    public PageInfo<R> get(P param) {
        return getService().get(param);
    }

    @PostMapping
    @Operation(summary = "新建接口")
    public void save(@RequestBody P param) {
        param.setId(null);
        getService().save(param);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新接口")
    public void update(@PathVariable("id") Long id, @RequestBody P param) {
        param.setId(id);
        getService().save(param);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除接口")
    public void delete(@PathVariable("id") Long id) {
        getService().delete(id);
    }
}
