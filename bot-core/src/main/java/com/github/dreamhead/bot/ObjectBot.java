package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.Pair;
import com.rits.cloning.Cloner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.getDeclaredField;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;

public class ObjectBot {
    private Map<String, Object> container = new HashMap<>();
    private final Cloner cloner = new Cloner();

    public final ObjectBot define(final String name,
                                  final Object object) {
        container.put(name, object);
        return this;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final <T> T of(final String name, final Class<T> clazz, final Pair<String, ?>... pairs) {
        Object object = container.get(name);

        if (!clazz.isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Mismatch class [" + clazz.getName() + "] found");
        }

        T existing = (T) object;

        if (pairs.length <= 0) {
            return clazz.cast(object);
        }

        T newObj = cloner.deepClone(existing);

        for (Pair<String, ?> pair : pairs) {
            String fieldName = pair.first();
            Optional<Field> field = getDeclaredField(clazz, fieldName);
            if (field.isPresent()) {
                setFieldValue(newObj, field.get(), pair.second());
            } else {
                throw new IllegalArgumentException("No field [" + fieldName + "] found");
            }
        }

        return newObj;
    }
}
