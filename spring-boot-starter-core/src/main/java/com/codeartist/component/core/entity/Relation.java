package com.codeartist.component.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 表关联参数
 *
 * @author AiJiangnan
 * @since 2023-03-01
 */
@Getter
@Setter
public class Relation {

    /**
     * 1:N中的单条数据ID
     */
    @NotNull
    private Long id;

    /**
     * 1:N中的多个数据的ID
     */
    @NotNull
    @NotEmpty
    private Set<Long> ids;
}
