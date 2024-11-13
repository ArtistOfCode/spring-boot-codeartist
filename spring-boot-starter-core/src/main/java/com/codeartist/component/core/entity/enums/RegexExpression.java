package com.codeartist.component.core.entity.enums;

/**
 * 正则表达式
 */
public interface RegexExpression {

    /**
     * 手机号码正则表达式
     */
    String PHONE_REGEX = "^1[345789]\\d{9}$";
    /**
     * 邮箱正则表达式
     */
    String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*@[A-Za-z0-9]+(([.\\-])[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 用户名正则表达式
     */
    String USERNAME_REGEX = "^[a-zA-Z]+\\w*$";
    /**
     * 密码正则表达式
     */
    String PASSWORD_REGEX = "^[\\w\\W]{6,16}$";
    /**
     * UUID正则表达式
     */
    String UUID_REGEX = "^[0-9a-f]{32}\\.\\w+";
    /**
     * 图片文件名称正则表达式
     */
    String IMAGE_REGEX = "^.+\\.(jpg|jpeg|png|bmp|svg)$";
    /**
     * PDF文件名称正则表达式
     */
    String PDF_REGEX = "^.+\\.(pdf)$";
}
