package com.github.dreamhead.bot.internal;

import org.objenesis.Objenesis;

public final class ObjenesisInstantiationStrategy {
    private final Objenesis objenesis;

    public ObjenesisInstantiationStrategy(final Objenesis objenesis) {
        this.objenesis = objenesis;
    }

    public <T> T newInstance(final Class<T> c) {
        return objenesis.newInstance(c);
    }
}
