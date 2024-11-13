package com.codeartist.component.core.entity.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数
 *
 * @author AiJiangnan
 * @date 2020/9/21
 */
@Getter
@Setter
public abstract class PageParam implements IdParam, UpdateParam {

    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNo = 1;
    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数")
    private Integer pageSize = 10;

    @Schema(description = "排序方式")
    private Boolean asc = false;

    @Schema(description = "排序字段")
    private String orderBy;

    public <T> IPage<T> page() {
        return this.page(200);
    }

    public <T> IPage<T> longPage() {
        return this.page(1000);
    }

    public <T> IPage<T> page(int maxPageSize) {
        this.pageNo = pageNo < 1 ? 1 : pageNo;
        this.pageSize = pageSize < 0 ? 10 : (pageSize >= maxPageSize ? maxPageSize : pageSize);
        return new Page<>(this.pageNo, this.pageSize);
    }
}
