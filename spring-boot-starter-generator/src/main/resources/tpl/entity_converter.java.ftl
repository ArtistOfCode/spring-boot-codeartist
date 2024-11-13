package ${package.Entity}.converter;

import com.codeartist.component.core.support.curd.BaseConverter;
import ${package.Entity}.${entity};
import ${package.Entity}.param.${entity}Param;
import ${package.Entity}.vo.${entity}VO;
import org.mapstruct.Mapper;

/**
 * ${table.comment!} 实体转换
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper(componentModel = "spring")
public interface ${entity}Converter extends BaseConverter<${entity}, ${entity}Param, ${entity}VO> {
}
