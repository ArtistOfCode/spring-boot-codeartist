package ${package.Entity}.vo;

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * ${table.comment!}
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
@Schema(description = "${table.comment!}")
public class ${entity}VO {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.comment!?length gt 0>
    @Schema(description = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}
