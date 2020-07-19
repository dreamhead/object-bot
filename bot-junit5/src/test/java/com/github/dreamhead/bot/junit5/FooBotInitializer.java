package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.BotInitializer;
import com.github.dreamhead.bot.ObjectBot;

public class FooBotInitializer implements BotInitializer {
    @Override
    public void initializer(final ObjectBot bot) {
        bot.define("defaultData", new Data("foo", "bar", 1, 1));
        bot.define("defaultData2", new Data("foo2", "bar2", 2, 2));
    }
}
