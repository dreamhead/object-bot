package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.FieldEntry;
import com.rits.cloning.Cloner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    public final <T> T of(final String name, final Class<T> clazz, final FieldEntry<?>... entries) {
        Object object = container.get(name);

        if (Objects.isNull(object)) {
            throw new IllegalArgumentException("No Bot [" + name + "] found");
        }

        return cloning(clazz, object, entries);
    }

    @SuppressWarnings("unchecked")
    private <T> T cloning(final Class<T> clazz, final Object object, final FieldEntry<?>[] entries) {
        if (!clazz.isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Mismatch class [" + clazz.getName() + "] found");
        }

        if (entries.length <= 0) {
            return clazz.cast(object);
        }

        validateEntries(entries);
        T existing = (T) object;
        T newObj = cloner.deepClone(existing);

        for (FieldEntry<?> entry : entries) {
            String fieldName = entry.name();
            Optional<Field> field = getDeclaredField(clazz, fieldName);
            if (field.isPresent()) {
                setFieldValue(newObj, field.get(), entry.value());
            } else {
                throw new IllegalArgumentException("No field [" + fieldName + "] found");
            }
        }

        return newObj;
    }

    private void validateEntries(final FieldEntry<?>[] entries) {
        Set<String> set = new HashSet<>();
        for (FieldEntry<?> entry : entries) {
            set.add(entry.name());
        }

        if (set.size() < entries.length) {
            throw new IllegalArgumentException("Duplicated name for entries is not allowed");
        }
    }
}
