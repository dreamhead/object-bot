package com.github.dreamhead.bot.junit5;

import com.github.dreamhead.bot.annotation.AnyField;
import com.github.dreamhead.bot.annotation.BooleanField;
import com.github.dreamhead.bot.annotation.Bot;
import com.github.dreamhead.bot.BotInitializer;
import com.github.dreamhead.bot.annotation.BotWith;
import com.github.dreamhead.bot.annotation.ByteField;
import com.github.dreamhead.bot.annotation.CharField;
import com.github.dreamhead.bot.annotation.ClassField;
import com.github.dreamhead.bot.annotation.DoubleField;
import com.github.dreamhead.bot.annotation.FieldFactory;
import com.github.dreamhead.bot.annotation.FloatField;
import com.github.dreamhead.bot.annotation.IntField;
import com.github.dreamhead.bot.annotation.LongField;
import com.github.dreamhead.bot.ObjectBot;
import com.github.dreamhead.bot.annotation.ShortField;
import com.github.dreamhead.bot.annotation.StringField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BotExtension.class)
@BotWith(RepeatableFieldTest.class)
public class RepeatableFieldTest implements BotInitializer {
    @Override
    public void initialize(final ObjectBot bot) {
        bot.define("longFields", new LongFieldsData(1, 2));
        bot.define("intFields", new IntFieldsData(1, 2));
        bot.define("stringFields", new StringFieldsData("foo1", "foo2"));
        bot.define("anyFields", new AnyFieldsData("hello", 1L));
        bot.define("doubleFields", new DoubleFieldsData(-1.1, -1.2));
        bot.define("floatFields", new FloatFieldsData(-1.1f, -1.2f));
        bot.define("charFields", new CharFieldsData('a', 'b'));
        bot.define("booleanFields", new BooleanFieldsData(true, true));
        bot.define("shortFields", new ShortFieldsData((short)-1, (short)-2));
        bot.define("byteFields", new ByteFieldsData((byte)-1, (byte)-2));
        bot.define("classFields", new ClassFieldsData(Integer.class, Long.class));
    }

    @Bot("longFields")
    @LongField(name = "field1", value = 2)
    @LongField(name = "field2", value = 3)
    private LongFieldsData longFieldsData;

    @Test
    void should_have_long_fields() {
        assertThat(longFieldsData.field1).isEqualTo(2);
        assertThat(longFieldsData.field2).isEqualTo(3);
    }

    @Bot("intFields")
    @IntField(name = "field1", value = 2)
    @IntField(name = "field2", value = 3)
    private IntFieldsData intFieldsData;

    @Test
    void should_have_int_fields() {
        assertThat(intFieldsData.field1).isEqualTo(2);
        assertThat(intFieldsData.field2).isEqualTo(3);
    }

    @Bot("stringFields")
    @StringField(name = "field1", value = "bar1")
    @StringField(name = "field2", value = "bar2")
    private StringFieldsData stringFieldsData;

    @Test
    void should_have_string_fields() {
        assertThat(stringFieldsData.field1).isEqualTo("bar1");
        assertThat(stringFieldsData.field2).isEqualTo("bar2");
    }

    @Bot("anyFields")
    @AnyField(name = "field1", factory = AnyField1Factory.class)
    @AnyField(name = "field2", factory = AnyField2Factory.class)
    private AnyFieldsData anyFieldsData;

    @Test
    void should_have_any_fields() {
        assertThat(anyFieldsData.field1).isEqualTo("world");
        assertThat(anyFieldsData.field2).isEqualTo(2L);
    }

    @Bot("doubleFields")
    @DoubleField(name = "field1", value = 1.0)
    @DoubleField(name = "field2", value = 2.0)
    private DoubleFieldsData doubleFieldData;

    @Test
    void should_have_double_fields() {
        assertThat(doubleFieldData.field1).isEqualTo(1.0);
        assertThat(doubleFieldData.field2).isEqualTo(2.0);
    }

    @Bot("floatFields")
    @FloatField(name = "field1", value = 1.0f)
    @FloatField(name = "field2", value = 2.0f)
    private FloatFieldsData floatFieldData;

    @Test
    void should_have_float_fields() {
        assertThat(floatFieldData.field1).isEqualTo(1.0f);
        assertThat(floatFieldData.field2).isEqualTo(2.0f);
    }

    @Bot("charFields")
    @CharField(name = "field1", value = 'x')
    @CharField(name = "field2", value = 'y')
    private CharFieldsData charFieldData;

    @Test
    void should_have_char_fields() {
        assertThat(charFieldData.field1).isEqualTo('x');
        assertThat(charFieldData.field2).isEqualTo('y');
    }

    @Bot("booleanFields")
    @BooleanField(name = "field1", value = false)
    @BooleanField(name = "field2", value = false)
    private BooleanFieldsData booleanFieldData;

    @Test
    void should_have_boolean_fields() {
        assertThat(booleanFieldData.field1).isEqualTo(false);
        assertThat(booleanFieldData.field2).isEqualTo(false);
    }

    @Bot("shortFields")
    @ShortField(name = "field1", value = 1)
    @ShortField(name = "field2", value = 2)
    private ShortFieldsData shortFieldData;

    @Test
    void should_have_short_fields() {
        assertThat(shortFieldData.field1).isEqualTo((short)1);
        assertThat(shortFieldData.field2).isEqualTo((short)2);
    }

    @Bot("byteFields")
    @ByteField(name = "field1", value = 1)
    @ByteField(name = "field2", value = 2)
    private ByteFieldsData byteFieldData;

    @Test
    void should_have_byte_fields() {
        assertThat(byteFieldData.field1).isEqualTo((byte)1);
        assertThat(byteFieldData.field2).isEqualTo((byte)2);
    }

    @Bot("classFields")
    @ClassField(name = "field1", value = Boolean.class)
    @ClassField(name = "field2", value = Class.class)
    private ClassFieldsData classFieldData;

    @Test
    void should_have_class_fields() {
        assertThat(classFieldData.field1).isEqualTo(Boolean.class);
        assertThat(classFieldData.field2).isEqualTo(Class.class);
    }

    private static class LongFieldsData {
        private long field1;
        private long field2;

        public LongFieldsData(final long field1, final long field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class IntFieldsData {
        private int field1;
        private int field2;

        public IntFieldsData(final int field1, final int field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class StringFieldsData {
        private String field1;
        private String field2;

        public StringFieldsData(final String field1, final String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class AnyFieldsData {
        private Object field1;
        private Object field2;

        public AnyFieldsData(final Object field1, final Object field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class AnyField1Factory implements FieldFactory<String> {
        @Override
        public String getValue() {
            return "world";
        }
    }

    private static class AnyField2Factory implements FieldFactory<Long> {
        @Override
        public Long getValue() {
            return 2L;
        }
    }

    private static class DoubleFieldsData {
        private double field1;
        private double field2;

        public DoubleFieldsData(final double field1, final double field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class FloatFieldsData {
        private float field1;
        private float field2;

        public FloatFieldsData(final float field1, final float field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class CharFieldsData {
        private char field1;
        private char field2;

        public CharFieldsData(final char field1, final char field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class BooleanFieldsData {
        private boolean field1;
        private boolean field2;

        public BooleanFieldsData(final boolean field1, final boolean field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class ShortFieldsData {
        private short field1;
        private short field2;

        public ShortFieldsData(final short field1, final short field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class ByteFieldsData {
        private byte field1;
        private byte field2;

        public ByteFieldsData(final byte field1, final byte field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    private static class ClassFieldsData {
        private Class<?> field1;
        private Class<?> field2;

        public ClassFieldsData(final Class<?> field1,
                               final Class<?> field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }
}
