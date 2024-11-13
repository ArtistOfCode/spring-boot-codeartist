package com.codeartist.component.core.support.mq;

import java.util.HashMap;

/**
 * MQ请求头
 *
 * @author AiJiangnan
 * @date 2023/5/4
 */
public class MqHeaders extends HashMap<String, String> {

    public static final String DEFAULT_TAG = "*";
    public static final String MESSAGE_ID = "id";
    public static final String BODY_KEY = "b";

    public String getId() {
        return this.get(MESSAGE_ID);
    }

    public void setId(String id) {
        this.put(MESSAGE_ID, id);
    }
}
