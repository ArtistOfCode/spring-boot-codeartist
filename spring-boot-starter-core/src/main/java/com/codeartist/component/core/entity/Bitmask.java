package com.codeartist.component.core.entity;


/**
 * 位状态掩码
 *
 * @author AiJiangnan
 * @date 2025/2/26
 */
@FunctionalInterface
public interface Bitmask {

    /**
     * 位掩码
     * <p>
     * <pre><code>
     * public static final int STATE_A = 1 << 0; // 0001
     * public static final int STATE_B = 1 << 1; // 0010
     * public static final int STATE_C = 1 << 2; // 0100
     * public static final int STATE_D = 1 << 3; // 1000
     * </code></pre>
     */
    int getBit();

    /**
     * 开启状态
     */
    default int on(int flag) {
        return flag | getBit();
    }

    /**
     * 关闭状态
     */
    default int off(int flag) {
        return flag & ~getBit();
    }

    /**
     * 切换状态
     */
    default int toggle(int flag) {
        return flag ^ getBit();
    }

    /**
     * 判断状态是否开启
     */
    default boolean is(int flag) {
        return (flag & getBit()) == getBit();
    }
}
