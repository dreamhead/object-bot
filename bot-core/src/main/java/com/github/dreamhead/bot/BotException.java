package com.github.dreamhead.bot;

public class BotException extends RuntimeException {
    public BotException() {
        super();
    }

    public BotException(final String message) {
        super(message);
    }

    public BotException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BotException(final Throwable cause) {
        super(cause);
    }

    protected BotException(final String message,
                           final Throwable cause,
                           final boolean enableSuppression,
                           final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
