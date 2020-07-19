package com.github.dreamhead.bot.util;

public final class Pair<T, U> {
    private final T first;
    private final U second;

    private Pair(final T first, final U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    public static <T, U> Pair<T, U> of(final T first, final U second) {
        return new Pair<>(first, second);
    }
}
