package com.codeartist.component.cache.sample.test.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试类
 *
 * @author AiJiangnan
 * @date 2023/7/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericDemo<T> {
    private String name;
    private T body;
}
