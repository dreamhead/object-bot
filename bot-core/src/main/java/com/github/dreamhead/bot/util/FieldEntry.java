package com.github.dreamhead.bot.util;

public final class FieldEntry<T> {
    private final String name;
    private final T value;

    private FieldEntry(final String name, final T value) {
        this.name = name;
        this.value = value;
    }

    public String name() {
        return name;
    }

    public T value() {
        return value;
    }

    public static FieldEntryBuilder name(final String name) {
        return new FieldEntryBuilder(name);
    }

    public static class FieldEntryBuilder {
        private final String name;

        public FieldEntryBuilder(final String name) {
            this.name = name;
        }

        public <T> FieldEntry<T> value(final T value) {
            return new FieldEntry<>(name, value);
        }
    }
}
