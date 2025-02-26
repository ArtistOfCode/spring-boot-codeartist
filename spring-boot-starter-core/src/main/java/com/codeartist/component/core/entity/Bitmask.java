package com.codeartist.component.core.entity;


/**
 * 位状态掩码
 *
 * @author AiJiangnan
 * @date 2025/2/26
 */
@FunctionalInterface
public interface Bitmask {

    int getBit();

    /**
     * 开启状态
     */
    default int turnOn(int flag) {
        return flag | getBit();
    }

    /**
     * 关闭状态
     */
    default int turnOff(int flag) {
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
    default boolean isStatus(int flag) {
        return (flag & getBit()) == getBit();
    }
}
