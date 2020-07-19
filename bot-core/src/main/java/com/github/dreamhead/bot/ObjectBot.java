package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.Pair;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.getField;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.newInstance;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.setField;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.withAccessible;

public class ObjectBot {
    private Map<String, Object> container = new HashMap<>();

    public final ObjectBot define(final String name,
                                  final Object object) {
        container.put(name, object);
        return this;
    }

    @SafeVarargs
    public final <T> T of(final String name, final Class<T> clazz, final Pair<String, ?>... pairs) {
        Object object = container.get(name);

        if (pairs.length <= 0) {
            return clazz.cast(object);
        }

        T newObj = newInstance(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            copyField(object, newObj, field, pairs);
        }

        return newObj;
    }

    @SafeVarargs
    private final <T> void copyField(final T oldObj,
                                     final T newObj,
                                     final Field field,
                                     final Pair<String, ?>... pairs) {
        withAccessible(field, (accessible) -> {
            Object fieldValue = getFieldValue(oldObj, field, pairs);
            setField(newObj, field, fieldValue);
            return null;
        });
    }

    private <T> Object getFieldValue(final T oldObj,
                                     final Field field,
                                     final Pair<String, ?>[] pairs) {
        for (Pair<String, ?> pair : pairs) {
            if (field.getName().equals(pair.first())) {
                return pair.second();
            }
        }

        return getField(oldObj, field);
    }
}
