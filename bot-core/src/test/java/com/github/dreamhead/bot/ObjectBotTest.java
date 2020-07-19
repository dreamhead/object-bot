package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectBotTest {
    @Test
    public void should_define_object() {
        ObjectBot bot = new ObjectBot();
        bot.define("hello", new Data("foo", "bar"))
                .define("world", new Data("foo", "bar"));
        Data data = bot.of("hello", Data.class);
        assertThat(data.getField1()).isEqualTo("foo");
        assertThat(data.getField2()).isEqualTo("bar");
    }

    @Test
    public void should_override_fields() {
        ObjectBot bot = new ObjectBot();
        bot.define("hello", new Data("foo", "bar"));
        Data data = bot.of("hello", Data.class, Pair.of("field1", "foo1"));
        assertThat(data.getField1()).isEqualTo("foo1");
        assertThat(data.getField2()).isEqualTo("bar");
    }
}