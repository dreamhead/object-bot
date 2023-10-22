package com.github.dreamhead.bot.annotation;

import com.github.dreamhead.bot.BotInitializer;
import com.github.dreamhead.bot.FieldFillStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface BotWith {
    Class<? extends BotInitializer>[] value();

    FieldFillStrategy strategy() default FieldFillStrategy.DEFAULT;
}
