package com.codeartist.component.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "表关联信息")
public abstract class Relation {

    @NotNull
    @Schema(description = "1:N中的单个数据ID")
    private Long id;

    @NotNull
    @NotEmpty
    @Schema(description = "1:N中的多个数据的ID")
    private Set<Long> ids;

    @JsonIgnore
    private String one;
    @JsonIgnore
    private String more;
    @JsonIgnore
    private boolean invert;

    public Relation(String one, String more) {
        this(one, more, false);
    }

    public Relation(String one, String more, boolean invert) {
        if (invert) {
            this.one = more;
            this.more = one;
        } else {
            this.one = one;
            this.more = more;
        }
        this.invert = invert;
    }
}
