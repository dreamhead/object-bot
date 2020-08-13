package com.github.dreamhead.bot;

import com.github.dreamhead.bot.util.FieldEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectBotTest {

    private ObjectBot bot;

    @BeforeEach
    void setUp() {
        this.bot = new ObjectBot();
    }

    @Test
    public void should_define_object() {
        bot.define("hello", new Data("foo", "bar"))
                .define("world", new Data("foo", "bar"));
        Data data = bot.of("hello", Data.class);
        assertThat(data.getField1()).isEqualTo("foo");
        assertThat(data.getField2()).isEqualTo("bar");
    }

    @Test
    public void should_override_fields() {
        bot.define("hello", new Data("foo", "bar"));
        Data data = bot.of("hello", Data.class, FieldEntry.of("field1", "foo1"));
        assertThat(data.getField1()).isEqualTo("foo1");
        assertThat(data.getField2()).isEqualTo("bar");
    }

    @Test
    public void should_throw_exception_for_mismatch_type() {
        bot.define("hello", new Data("foo", "bar"));
        assertThrows(IllegalArgumentException.class, () ->
                bot.of("hello", String.class));
    }

    @Test
    public void should_throw_exception_for_unknown_field() {
        bot.define("hello", new Data("foo", "bar"));
        assertThrows(IllegalArgumentException.class, () ->
                bot.of("hello", Data.class, FieldEntry.of("unknown", "foo1")));
    }

    @Test
    public void should_not_allow_same_name_field_entry() {
        bot.define("hello", new Data("foo", "bar"));
        assertThrows(IllegalArgumentException.class, () ->
                bot.of("hello", Data.class,
                        FieldEntry.of("field1", "foo1"),
                        FieldEntry.of("field1", "foo2")
                ));
    }

    @Test
    public void should_not_throw_exception_for_unknown_bot() {
        assertThrows(IllegalArgumentException.class, () ->
                bot.of("hello", Data.class,
                        FieldEntry.of("field1", "foo2")
                ));
    }
}