package com.codeartist.component.core.support.uuid;

/**
 * ID生成器
 *
 * @author AiJiangnan
 * @date 2023/2/21
 */
public interface IdGenerator {

    Long nextID();

    String nextUUID();
}
