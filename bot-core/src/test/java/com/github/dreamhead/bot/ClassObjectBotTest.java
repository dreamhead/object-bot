package com.github.dreamhead.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.dreamhead.bot.ObjectBot.field;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassObjectBotTest {
    private ObjectBot bot;

    @BeforeEach
    void setUp() {
        this.bot = new ObjectBot();
    }

    @Test
    public void should_define_class() {
        bot.define("hello", Data.class)
                .define("world", Data.class);
        Data data = bot.of("hello", Data.class,
                field("field1").value("foo"),
                field("field2").value("bar"));
        assertThat(data.getField1()).isEqualTo("foo");
        assertThat(data.getField2()).isEqualTo("bar");
    }
}
