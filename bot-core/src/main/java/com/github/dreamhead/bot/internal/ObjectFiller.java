package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;

public class ObjectFiller {

    public static final int DEFAULT_LENGTH = 10;

    public Object newValue(final Class<?> clazz, final FieldFillStrategy fillStrategy) {
        if (clazz == String.class) {
            return RandomStringUtils.randomAlphabetic(DEFAULT_LENGTH);
        }

        return null;
    }

    public Object fill(final Object entry, final FieldFillStrategy fillStrategy) {
        Class<?> clazz = entry.getClass();
        Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> !field.getType().isPrimitive())
                .forEach(field -> {
                    field.setAccessible(true);
                    setFieldValue(entry, field, newValue(field.getType(), fillStrategy));
                });

        return entry;
    }
}
