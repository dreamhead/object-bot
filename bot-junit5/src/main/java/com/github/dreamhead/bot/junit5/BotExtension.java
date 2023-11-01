package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.FieldFillStrategy;
import com.github.dreamhead.bot.annotation.AnyField;
import com.github.dreamhead.bot.annotation.BooleanField;
import com.github.dreamhead.bot.annotation.Bot;
import com.github.dreamhead.bot.BotInitializer;
import com.github.dreamhead.bot.annotation.BotWith;
import com.github.dreamhead.bot.annotation.ByteField;
import com.github.dreamhead.bot.annotation.CharField;
import com.github.dreamhead.bot.annotation.ClassField;
import com.github.dreamhead.bot.annotation.DoubleField;
import com.github.dreamhead.bot.annotation.FieldFactory;
import com.github.dreamhead.bot.annotation.FloatField;
import com.github.dreamhead.bot.annotation.IntField;
import com.github.dreamhead.bot.annotation.LongField;
import com.github.dreamhead.bot.ObjectBot;
import com.github.dreamhead.bot.annotation.ShortField;
import com.github.dreamhead.bot.annotation.StringField;
import com.github.dreamhead.bot.util.FieldEntry;
import com.google.common.base.Strings;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.dreamhead.bot.ObjectBot.field;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.findAnnotatedFields;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.findAnnotation;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.newInstance;
import static com.github.dreamhead.bot.reflection.ReflectionSupport.setFieldValue;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

public class BotExtension implements BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor {
    private static final ExtensionContext.Namespace BOT = create("com.github.dreamhead.bot");

    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext context) {
        Class<?> testInstanceClass = testInstance.getClass();
        ObjectBot bot = context.getStore(BOT).get(testInstanceClass, ObjectBot.class);

        List<Field> fields = findAnnotatedFields(testInstanceClass, Bot.class);
        for (Field field : fields) {
            Bot annotation = field.getAnnotation(Bot.class);
            List<FieldEntry<Object>> pairs = getModifiers(field);
            Object value = bot.of(asBotName(annotation, field), field.getType(), pairs.toArray(new FieldEntry[0]));
            setFieldValue(testInstance, field, value);
        }
    }

    private String asBotName(final Bot annotation, final Field field) {
        final String name = annotation.value();
        if (Strings.isNullOrEmpty(name)) {
            return field.getName();
        }

        return name;
    }

    private List<FieldEntry<Object>> getModifiers(final Field field) {
        List<FieldEntry<Object>> stringModifiers = getModifiers(field, StringField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> longModifiers = getModifiers(field, LongField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> intModifiers = getModifiers(field, IntField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> doubleModifiers = getModifiers(field, DoubleField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> floatModifiers = getModifiers(field, FloatField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> charModifiers = getModifiers(field, CharField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> booleanModifiers = getModifiers(field, BooleanField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> shortModifiers = getModifiers(field, ShortField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> byteModifiers = getModifiers(field, ByteField.class,
                annotation -> field(annotation.name()).value(annotation.value()));
        List<FieldEntry<Object>> classModifiers = getModifiers(field, ClassField.class,
                annotation -> field(annotation.name()).value(annotation.value()));

        List<FieldEntry<Object>> anyModifiers = getModifiers(field, AnyField.class,
                this::getAnyValue);

        return Stream.of(stringModifiers, longModifiers, intModifiers,
                anyModifiers, doubleModifiers, floatModifiers, charModifiers,
                booleanModifiers, shortModifiers, byteModifiers, classModifiers)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private FieldEntry<Object> getAnyValue(final AnyField annotation) {
        Class<? extends FieldFactory<?>> factory = annotation.factory();
        return field(annotation.name()).value(newInstance(factory).getValue());
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation>
    List<FieldEntry<Object>> getModifiers(final Field field,
                                            final Class<T> annotationClass,
                                            final Function<T, FieldEntry<Object>> function) {
        Annotation[] annotations = field.getAnnotationsByType(annotationClass);
        if (annotations.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(annotations)
                .map(annotation -> function.apply((T) annotation))
                .collect(toList());
    }

    private ObjectBot createBot(final BotWith botWith) {
        Class<? extends BotInitializer>[] factories = botWith.value();
        BotInitializer initializer = newInstance(factories[0]);
        ObjectBot bot = new ObjectBot(botWith.strategy());
        initializer.initialize(bot);
        return bot;
    }

    @Override
    public void beforeAll(final ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();

        Optional<BotWith> annotation = findBotAnnotation(context);
        annotation.ifPresent(botWith -> {
            ObjectBot bot = createBot(botWith);
            context.getStore(BOT).put(testClass, bot);
        });

        if (!annotation.isPresent()) {
            context.getStore(BOT).put(testClass, new ObjectBot(FieldFillStrategy.RANDOM));
        }
    }

    private Optional<BotWith> findBotAnnotation(final ExtensionContext context) {
        Optional<AnnotatedElement> element = context.getElement();
        return element.flatMap(annotatedElement ->
                findAnnotation(annotatedElement, BotWith.class));
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        context.getStore(BOT).remove(testClass);
    }
}
