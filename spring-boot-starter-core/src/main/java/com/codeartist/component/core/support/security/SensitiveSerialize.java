package com.codeartist.component.core.support.security;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * 脱敏JSON序列化配置
 *
 * @author AiJiangnan
 * @date 2025/1/3
 */
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveFormat annotation;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtils.isEmpty(value)) {
            return;
        }

        int prefix;
        int suffix;

        SensitiveType type = annotation.type();
        if (SensitiveType.CUSTOM == type) {
            suffix = annotation.suffix();
            prefix = annotation.prefix();
        } else {
            prefix = type.getPrefix();
            suffix = type.getSuffix();
        }

        if (prefix < 0 || suffix < 0 || (prefix == 0 && suffix == 0)) {
            gen.writeString(value);
            return;
        }

        char mask = annotation.mask();
        int length = value.length();

        if (prefix + suffix >= length) {
            gen.writeString(mask(value, 0, length, mask));
            return;
        }

        gen.writeString(mask(value, prefix, suffix, mask));
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        SensitiveFormat annotation = property.getAnnotation(SensitiveFormat.class);
        return new SensitiveSerialize(annotation);
    }

    private String mask(String str, int start, int end, char maskChar) {
        char[] chars = str.toCharArray();
        for (int i = start; i < chars.length - end; i++) {
            chars[i] = maskChar;
        }
        return new String(chars);
    }
}
