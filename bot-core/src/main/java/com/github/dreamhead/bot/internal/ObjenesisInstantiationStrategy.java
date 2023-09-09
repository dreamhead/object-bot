package com.github.dreamhead.bot.internal;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public final class ObjenesisInstantiationStrategy {
    private static final ObjenesisInstantiationStrategy INSTANCE = new ObjenesisInstantiationStrategy();
    private final Objenesis objenesis = new ObjenesisStd();

    public <T> T newInstance(final Class<T> c) {
        return objenesis.newInstance(c);
    }

    public static ObjenesisInstantiationStrategy getInstance() {
        return INSTANCE;
    }
}
