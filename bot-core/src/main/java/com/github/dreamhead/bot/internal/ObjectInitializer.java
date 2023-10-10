package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;
import org.jeasy.random.EasyRandom;

public class ObjectInitializer {
    private static final ObjectInitializer INSTANCE = new ObjectInitializer();

    public Object newInstance(final Class<?> clazz, final FieldFillStrategy filedValueStrategy) {
        EasyRandom random = new EasyRandom();
        return random.nextObject(clazz);
    }

    public static ObjectInitializer getInstance() {
        return INSTANCE;
    }

}
