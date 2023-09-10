package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;
import org.objenesis.ObjenesisStd;

public class ObjectInitializer {
    private static final ObjectInitializer INSTANCE = new ObjectInitializer(
            new ObjenesisInstantiationStrategy(new ObjenesisStd()));
    private ObjenesisInstantiationStrategy strategy;
    private ObjectFiller filler;

    public ObjectInitializer(final ObjenesisInstantiationStrategy strategy) {
        this.strategy = strategy;
        this.filler = new ObjectFiller(strategy);
    }

    public Object newInstance(final Class<?> clazz, final FieldFillStrategy filedValueStrategy) {
        Object obj = strategy.newInstance(clazz);
        return filler.fill(obj, filedValueStrategy);
    }

    public static ObjectInitializer getInstance() {
        return INSTANCE;
    }

}
