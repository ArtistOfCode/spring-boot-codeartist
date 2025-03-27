package com.codeartist.component.core.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 服务工具类
 *
 * @author AiJiangnan
 * @date 2025/3/27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceUtils {

    public static <D> void iterate(BiFunction<Long, Integer, List<D>> func,
                                   int batchSize,
                                   Function<D, Long> getId,
                                   Consumer<D> consumer) {
        iterateBatch(func, batchSize, getId, ds -> ds.forEach(consumer));
    }

    public static <D> void iterateBatch(BiFunction<Long, Integer, List<D>> func,
                                        int batchSize,
                                        Function<D, Long> getId,
                                        Consumer<List<D>> consumer) {
        long startId = 0L;
        while (true) {
            List<D> list = func.apply(startId, batchSize);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            consumer.accept(list);
            if (list.size() < batchSize) {
                break;
            }
            startId = getId.apply(list.get(batchSize - 1));
        }
    }
}
