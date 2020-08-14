package com.github.dreamhead.bot.reflection;

import com.github.dreamhead.bot.BotException;
import com.google.common.collect.ImmutableList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.dreamhead.bot.util.Preconditions.checkNotNullElements;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public final class ReflectionSupport {
    public static <T> T newInstance(final Class<T> clazz, final Object... args) {
        requireNonNull(clazz, "Class must not be null");
        requireNonNull(args, "Argument array must not be null");
        checkNotNullElements(args, "Individual arguments must not be null");

        try {
            Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
            return newInstance(clazz.getDeclaredConstructor(parameterTypes), args);
        } catch (Throwable t) {
            throw new BotException(t);
        }
    }

    public static <T> T newInstance(final Constructor<T> constructor, final Object... args) {
        requireNonNull(constructor, "Constructor must not be null");

        return withAccessible(constructor, (ctor) -> {
            try {
                return constructor.newInstance(args);
            } catch (Throwable t) {
                throw new BotException(t);
            }
        });
    }

    public static <T extends AccessibleObject, U> U withAccessible(final T object, final Function<T, U> function) {
        requireNonNull(object, "Accessible object must not be null");
        requireNonNull(function, "Function must not be null");

        boolean oldAccessible = object.isAccessible();
        if (!oldAccessible) {
            object.setAccessible(true);
        }

        U result = function.apply(object);

        if (!oldAccessible) {
            object.setAccessible(false);
        }

        return result;
    }

    public static <T> Optional<Field> getDeclaredField(final Class<T> clazz, final String name) {
        try {
            return Optional.of(clazz.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }

    public static <T, U> void setFieldValue(final T object, final Field field, final U value) {
        requireNonNull(object, "Object must not be null");
        requireNonNull(field, "Field must not be null");

        withAccessible(field, accessible -> {
            try {
                field.set(object, value);
                return value;
            } catch (IllegalAccessException e) {
                throw new BotException(e);
            }
        });
    }

    public static <T> Object getFieldValue(final T object, final Field field) {
        requireNonNull(object, "Object must not be null");
        requireNonNull(field, "Field must not be null");

        return withAccessible(field, accessible -> {
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                throw new BotException(e);
            }
        });
    }

    public static ImmutableList<Field> findAllFields(final Class<?> clazz, final Predicate<Field> predicate) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(predicate, "Predicate must not be null");

        List<Field> localFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .collect(toList());

        return localFields.stream()
                .filter(predicate)
                .collect(toImmutableList());
    }

    public static List<Field> findAnnotatedFields(final Class<?> clazz,
                                                  final Class<? extends Annotation> annotationType) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(annotationType, "Annotation type must not be null");

        return findAllFields(clazz, field -> field.isAnnotationPresent(annotationType));
    }

    public static <A extends Annotation> Optional<A> findAnnotation(final AnnotatedElement element,
                                                                     final Class<A> annotationType) {

        requireNonNull(annotationType, "Annotation type must not be null");

        if (element == null) {
            return Optional.empty();
        }

        A annotation = element.getDeclaredAnnotation(annotationType);
        if (annotation != null) {
            return Optional.of(annotation);
        }

        return Optional.empty();
    }

    private ReflectionSupport() {
    }
}
