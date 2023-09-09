package com.github.dreamhead.bot.internal;

public class ClassSlot implements Slot {
    private Class<?> clazz;

    public ClassSlot(final Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<?> getEntryClass() {
        return clazz;
    }

    @Override
    public Object getEntry() {
        return ObjenesisInstantiationStrategy.getInstance().newInstance(this.clazz);
    }
}
