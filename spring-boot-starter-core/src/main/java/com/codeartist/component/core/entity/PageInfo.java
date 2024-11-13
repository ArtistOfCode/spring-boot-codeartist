package com.codeartist.component.core.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codeartist.component.core.support.curd.BaseConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页响应实体
 *
 * @param <R> 响应实体类型
 *
 * @author AiJiangnan
 * @date 2020/9/21
 */
@Getter
@Setter
public class PageInfo<R> {

    /**
     * 当前页码
     */
    private int current;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 记录数据
     */
    private List<R> records = Collections.emptyList();

    public PageInfo() {
    }

    public PageInfo(int current, int total) {
        this.current = current;
        this.total = total;
    }

    public PageInfo(int current, int total, List<R> records) {
        this.current = current;
        this.total = total;
        this.records = records;
    }

    public <D> PageInfo(IPage<D> page, Function<List<D>, List<R>> func) {
        this((int) page.getCurrent(), (int) page.getTotal(), func.apply(page.getRecords()));
    }

    public <D> PageInfo(IPage<D> page, BaseConverter<D, ?, R> converter) {
        this((int) page.getCurrent(), (int) page.getTotal(), converter.toVo(page.getRecords()));
    }
}
