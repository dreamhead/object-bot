package com.github.dreamhead.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.dreamhead.bot.ObjectBot.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

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

    @Test
    public void should_define_class_with_random_field_value() {
        bot.define("hello", Data.class, FieldFillStrategy.RANDOM)
                .define("world", Data.class);
        Data data = bot.of("hello", Data.class);
        assertThat(data.getField1()).isNotNull();
        assertThat(data.getField2()).isNotNull();
    }

    @Test
    public void should_define_class_with_random_field_value_for_customized_field() {
        bot.define("hello", BigData.class, FieldFillStrategy.RANDOM);
        BigData data = bot.of("hello", BigData.class);
        assertThat(data.getData().getField1()).isNotNull();
        assertThat(data.getData().getField2()).isNotNull();
        assertThat(data.getIntValue()).isNotZero();
        assertThat(data.getIntegerValue()).isNotNull();
        assertThat(data.getDoubleValue()).isNotZero();
        assertThat(data.getADoubleValue()).isNotNull();
        assertThat(data.getLongValue()).isNotZero();
        assertThat(data.getALongValue()).isNotNull();
        assertThat(data.getFloatValue()).isNotZero();
        assertThat(data.getAFloatValue()).isNotNull();
        assertThat(data.getCharValue()).isNotEqualTo((char)0);
        assertThat(data.getCharacterValue()).isNotNull();
        assertThat(data.getShortValue()).isNotZero();
        assertThat(data.getAShortValue()).isNotNull();
        assertThat(data.getByteValue()).isNotZero();
        assertThat(data.getAByteValue()).isNotNull();
        assertThat(data.isBooleanValue()).isIn(true, false);
        assertThat(data.getABooleanValue()).isNotNull();

        assertThat(data.getVoidValue()).isNull();
    }
}
