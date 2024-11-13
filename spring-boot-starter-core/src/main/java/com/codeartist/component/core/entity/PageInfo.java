package com.codeartist.component.core.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页响应实体
 *
 * @author AiJiangnan
 * @date 2020/9/21
 */
@Getter
@Setter
public class PageInfo<T> {

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
    private List<T> records = Collections.emptyList();

    public PageInfo() {
    }

    public PageInfo(int current, int total) {
        this.current = current;
        this.total = total;
    }

    public PageInfo(int current, int total, List<T> records) {
        this.current = current;
        this.total = total;
        this.records = records;
    }

    public <R> PageInfo(IPage<R> page, Function<List<R>, List<T>> func) {
        this((int) page.getCurrent(), (int) page.getTotal(), func.apply(page.getRecords()));
    }
}
