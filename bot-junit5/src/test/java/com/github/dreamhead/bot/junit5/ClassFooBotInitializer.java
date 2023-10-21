package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.BotInitializer;
import com.github.dreamhead.bot.ObjectBot;

public class ClassFooBotInitializer implements BotInitializer {
    @Override
    public void initialize(ObjectBot bot) {
        bot.define("classdata", Data.class);
    }
}
