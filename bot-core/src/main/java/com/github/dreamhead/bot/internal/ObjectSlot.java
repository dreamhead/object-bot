package com.github.dreamhead.bot.internal;

public class ObjectSlot implements Slot {
    private final Object entry;

    public ObjectSlot(final Object entry) {
        this.entry = entry;
    }

    @Override
    public Class<?> getEntryClass() {
        return entry.getClass();
    }

    @Override
    public Object getEntry() {
        return entry;
    }
}
