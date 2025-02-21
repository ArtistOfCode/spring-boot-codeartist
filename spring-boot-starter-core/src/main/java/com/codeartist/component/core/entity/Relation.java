package com.codeartist.component.core.entity;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * 表关联参数
 *
 * @author AiJiangnan
 * @since 2023-03-01
 */
@Getter
@Setter
@Schema(description = "表关联信息")
public abstract class Relation<D> {

    @NotNull
    @Schema(description = "1:N中的单个数据ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Schema(description = "1:N中的多个数据的ID")
    private Set<Long> ids;

    @JsonIgnore
    private SFunction<D, Long> one;
    @JsonIgnore
    private SFunction<D, Long> more;
    @JsonIgnore
    private BiFunction<Long, Set<Long>, List<D>> mapper;

    public Relation(SFunction<D, Long> one, SFunction<D, Long> more) {
        this.one = one;
        this.more = more;
    }

    public Relation(SFunction<D, Long> one, SFunction<D, Long> more, BiFunction<Long, Set<Long>, List<D>> mapper) {
        this.one = one;
        this.more = more;
        this.mapper = mapper;
    }
}
