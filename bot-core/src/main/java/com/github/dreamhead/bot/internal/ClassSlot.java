package com.github.dreamhead.bot.internal;

import com.github.dreamhead.bot.FieldFillStrategy;

public class ClassSlot implements Slot {
    private final Class<?> clazz;
    private final FieldFillStrategy strategy;

    public ClassSlot(final Class<?> clazz, final FieldFillStrategy strategy) {
        this.clazz = clazz;
        this.strategy = strategy;
    }

    @Override
    public Class<?> getEntryClass() {
        return clazz;
    }

    @Override
    public Object getEntry() {
        return ObjectInitializer.getInstance().newInstance(clazz, strategy);
    }
}
