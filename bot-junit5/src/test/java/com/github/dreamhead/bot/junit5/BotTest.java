package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.annotation.AnyField;
import com.github.dreamhead.bot.annotation.Bot;
import com.github.dreamhead.bot.annotation.BotWith;
import com.github.dreamhead.bot.annotation.IntField;
import com.github.dreamhead.bot.annotation.LongField;
import com.github.dreamhead.bot.annotation.StringField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BotExtension.class)
@BotWith(FooBotInitializer.class)
public class BotTest {
    @Bot("defaultData")
    private Data data;

    @Bot("defaultData2")
    private Data data2;

    @Bot(value = "defaultData")
    @StringField(name = "field1", value="hello")
    @StringField(name = "field2", value="bar3")
    @LongField(name = "longField", value=3)
    @IntField(name = "intField", value=4)
    private Data data3;

    @Bot(value = "defaultData")
    @AnyField(name = "field1", factory = StringFieldValueFactory.class)
    private Data data4;

    @Test
    public void should_get_data() {
        assertThat(data.getField1()).isEqualTo("foo");
        assertThat(data.getField2()).isEqualTo("bar");
    }

    @Test
    public void should_get_data2() {
        assertThat(data2.getField1()).isEqualTo("foo2");
        assertThat(data2.getField2()).isEqualTo("bar2");
    }

    @Test
    public void should_get_data_with_modified_field() {
        assertThat(data3.getField1()).isEqualTo("hello");
        assertThat(data3.getField2()).isEqualTo("bar3");
        assertThat(data3.getLongField()).isEqualTo(3);
        assertThat(data3.getIntField()).isEqualTo(4);
    }

    @Test
    public void should_get_data_with_ant_field() {
        assertThat(data4.getField1()).isEqualTo("string");
    }
}
