package com.codeartist.component.core.support.uuid;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.UUID;

/**
 * 默认ID生成器
 *
 * @author AiJiangnan
 * @date 2023/2/24
 */
public class DefaultIdGenerator implements IdGenerator {

    @Override
    public Long nextID() {
        return IdWorker.getId();
    }

    @Override
    public String nextUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
