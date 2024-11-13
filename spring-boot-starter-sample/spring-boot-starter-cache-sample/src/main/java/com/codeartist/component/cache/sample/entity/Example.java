package com.codeartist.component.cache.sample.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author AiJiangnan
 * @date 2021/5/9
 */
@Data
public class Example {

    private String name;
    private Long age;
    private BigDecimal percent;
    private Example example;
}
