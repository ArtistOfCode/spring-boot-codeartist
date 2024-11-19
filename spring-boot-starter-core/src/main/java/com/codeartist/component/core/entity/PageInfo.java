package com.codeartist.component.core.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codeartist.component.core.support.curd.BaseConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页响应实体
 *
 * @param <R> 响应实体类型
 * @author AiJiangnan
 * @date 2020/9/21
 */
@Getter
@Setter
@Schema(description = "分页信息")
public class PageInfo<R> {

    @Schema(description = "当前页面")
    private int current;

    @Schema(description = "记录总数")
    private int total;

    @Schema(description = "记录数据")
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
