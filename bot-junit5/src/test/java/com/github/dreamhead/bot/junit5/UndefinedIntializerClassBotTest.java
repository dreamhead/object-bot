package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.FieldFillStrategy;
import com.github.dreamhead.bot.annotation.Bot;
import com.github.dreamhead.bot.annotation.BotWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BotExtension.class)
public class UndefinedIntializerClassBotTest {
    @Bot
    private Data data;

    @Test
    public void should_get_data_without_name() {
        assertThat(data.getField1()).isNotNull();
        assertThat(data.getField2()).isNotNull();
    }
}
