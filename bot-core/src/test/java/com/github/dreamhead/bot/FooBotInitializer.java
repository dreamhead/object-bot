package com.github.dreamhead.bot;

public class FooBotInitializer implements BotInitializer {
    @Override
    public void initializer(final ObjectBot bot) {
        bot.define("defaultData", new Data("foo", "bar"));
        bot.define("defaultData2", new Data("foo2", "bar2"));
    }
}
