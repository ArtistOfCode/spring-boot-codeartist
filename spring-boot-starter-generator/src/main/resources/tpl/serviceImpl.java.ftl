package ${package.Service};

import com.codeartist.component.core.support.curd.AbstractService;
import ${package.Entity}.${entity};
import ${package.Entity}.param.${entity}Param;
import ${package.Entity}.vo.${entity}VO;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${table.serviceName} extends AbstractService<${entity}, ${entity}VO, ${entity}Param> {

}
