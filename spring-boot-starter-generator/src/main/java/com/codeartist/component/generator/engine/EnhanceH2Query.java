package com.codeartist.component.generator.engine;

import com.baomidou.mybatisplus.generator.config.querys.H2Query;

/**
 * H2兼容查询类
 *
 * @author AiJiangnan
 * @date 2022/8/10
 */
public class EnhanceH2Query extends H2Query {

    @Override
    public String fieldKey() {
        return "INDEX_TYPE_NAME";
    }

    @Override
    public String fieldType() {
        return "DATA_TYPE";
    }
}
