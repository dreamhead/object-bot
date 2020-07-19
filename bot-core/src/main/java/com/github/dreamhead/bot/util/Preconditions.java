package com.github.dreamhead.bot.util;

import java.util.Objects;

public final class Preconditions {
    public static <T> T[] checkNotNullElements(final T[] array, final String message) {
        if (array != null) {
            for (T element : array) {
                Objects.requireNonNull(element, message);
            }
        }

        return array;
    }

    private Preconditions() {
    }
}
