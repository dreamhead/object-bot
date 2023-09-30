package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;

public class ObjectFiller {

    private static final int DEFAULT_LENGTH = 10;
    private static final int ALPHABETIC_LENGTH = 26;
    private final ObjenesisInstantiationStrategy strategy;
    private final Random rand = new Random();

    public ObjectFiller(final ObjenesisInstantiationStrategy strategy) {
        this.strategy = strategy;
    }

    public Object newValue(final Class<?> clazz, final FieldFillStrategy fillStrategy) {
        if (clazz == String.class) {
            return RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH);
        }

        if (clazz == Integer.TYPE || clazz == Integer.class) {
            return rand.nextInt();
        }

        if (clazz == Double.TYPE || clazz == Double.class) {
            return rand.nextDouble();
        }

        if (clazz == Long.TYPE || clazz == Long.class) {
            return rand.nextLong();
        }

        if (clazz == Float.TYPE || clazz == Float.class) {
            return rand.nextFloat();
        }

        if (clazz == Character.TYPE || clazz == Character.class) {
            return (char) (rand.nextInt(ALPHABETIC_LENGTH) + 'a');
        }

        if (clazz == Short.TYPE || clazz == Short.class) {
            return (short) rand.nextInt(Short.MAX_VALUE);
        }

        if (clazz == Byte.TYPE || clazz == Byte.class) {
            byte[] bytes = new byte[1];
            rand.nextBytes(bytes);
            return bytes[0];
        }

        if (clazz == Boolean.TYPE || clazz == Boolean.class) {
            return rand.nextBoolean();
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
