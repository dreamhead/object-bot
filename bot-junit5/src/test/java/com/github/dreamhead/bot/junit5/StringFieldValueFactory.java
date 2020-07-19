package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.FieldFactory;

public class StringFieldValueFactory implements FieldFactory<String> {
    @Override
    public String getValue() {
        return "string";
    }
}
