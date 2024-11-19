package com.codeartist.component.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "表关联信息")
public class Relation {

    @NotNull
    @Schema(description = "1:N中的单条数据ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Schema(description = "1:N中的多个数据的ID")
    private Set<Long> ids;
}
