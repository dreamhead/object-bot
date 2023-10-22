package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.FieldFillStrategy;
import com.github.dreamhead.bot.annotation.Bot;
import com.github.dreamhead.bot.annotation.BotWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BotExtension.class)
@BotWith(value = ClassFooBotInitializer.class, strategy = FieldFillStrategy.RANDOM)
public class ClassBotTest {
    @Bot("classdata")
    private Data data;

    @Bot
    private Data data2;

    @Test
    public void should_get_data() {
        assertThat(data.getField1()).isNotNull();
        assertThat(data.getField2()).isNotNull();
    }

    @Test
    public void should_get_data_without_name() {
        assertThat(data2.getField1()).isNotNull();
        assertThat(data2.getField2()).isNotNull();
    }
}
