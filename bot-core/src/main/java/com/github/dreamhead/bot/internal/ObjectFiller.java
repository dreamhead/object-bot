package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;

public class ObjectFiller {

    public static final int DEFAULT_LENGTH = 10;
    private final ObjenesisInstantiationStrategy strategy;

    public ObjectFiller(final ObjenesisInstantiationStrategy strategy) {
        this.strategy = strategy;
    }

    public Object newValue(final Class<?> clazz, final FieldFillStrategy fillStrategy) {
        if (clazz == String.class) {
            return RandomStringUtils.randomAlphabetic(DEFAULT_LENGTH);
        }

        if (clazz == Integer.TYPE) {
            Random rand = new Random();
            return rand.nextInt();
        }

        Object filedValue = this.strategy.newInstance(clazz);
        return fill(filedValue, fillStrategy);
    }

    public Object fill(final Object entry, final FieldFillStrategy fillStrategy) {
        Class<?> clazz = entry.getClass();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(this::shouldFill)
//                .filter(field -> !field.getType().isPrimitive())
                .forEach(field -> {
                    field.setAccessible(true);
                    setFieldValue(entry, field, newValue(field.getType(), fillStrategy));
                });

        return entry;
    }

    private boolean shouldFill(final Field field) {
        // Ignore generated fields
        if (field.getName().contains("$")) {
            return false;
        }

        return true;
    }
}
