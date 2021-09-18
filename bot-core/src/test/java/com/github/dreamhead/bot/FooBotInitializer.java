package com.github.dreamhead.bot;

import com.github.dreamhead.bot.annotation.BotInitializer;

public class FooBotInitializer implements BotInitializer {
    @Override
    public void initialize(final ObjectBot bot) {
        bot.define("defaultData", new Data("foo", "bar"));
        bot.define("defaultData2", new Data("foo2", "bar2"));
    }
}
