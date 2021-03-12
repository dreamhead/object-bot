package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.FieldEntry;
import com.rits.cloning.Cloner;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.github.dreamhead.bot.reflection.ReflectionSupport.getDeclaredField;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;

public class ObjectBot {
    private Map<String, Object> container = new HashMap<>();

    public final ObjectBot define(final String name,
                                  final Object object) {
        container.put(name, object);
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <T> T of(final String name, final Class<T> clazz, final FieldEntry<?>... entries) {
        Object object = container.get(name);

        if (Objects.isNull(object)) {
            throw new IllegalArgumentException("No Bot [" + name + "] found");
        }

        if (!clazz.isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Mismatch class [" + clazz.getName() + "] found for [" + name + "]");
        }

        return override((T) object, entries);
    }

    private static final Cloner CLONER;

    static {
        CLONER = new Cloner();
        CLONER.registerImmutable(ZonedDateTime.class);
    }

    public static FieldEntry.FieldEntryBuilder field(final String name) {
        return FieldEntry.name(name);
    }

    public static <T> T override(final T object, final FieldEntry<?>... entries) {
        validateEntries(entries);
        T newObj = CLONER.deepClone(object);
        Class<?> clazz = object.getClass();

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

    private static void validateEntries(final FieldEntry<?>[] entries) {
        long size = Arrays.stream(entries)
                .map(FieldEntry::name)
                .distinct()
                .count();

        if (size < entries.length) {
            throw new IllegalArgumentException("Duplicated name for entries is not allowed");
        }
    }
}
