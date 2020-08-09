# Object Bot

Object bot is a library for setting up Java objects as test data, which is inspired by [Factory Bot](https://github.com/thoughtbot/factory_bot).

## Quick Start

You have a POJO as your test data

```java
class Foo {
  private String field1;
  private String field2;
  
  public Foo(String field1, String field2) {
    this.field1 = field1;
    this.field2 = field2;
  }
  
  public String getField1() {
    return this.field1;
  }
  
  public String getField2() {
    return this.field2;
  }
}
```

And the you could initialize all your test POJO in initializer. 

```java
import com.github.dreamhead.bot.annotation.BotInitializer;

public class FooBotInitializer implements BotInitializer {
    @Override
    public void initializer(final ObjectBot bot) {
        // Give a name to identify your Pojo.
        bot.define("defaultFoo", new Foo("foo", "bar"));
    }
}
```

Now you can use it in your test. Refer the following Junit 5 example.

```java
@ExtendWith(BotExtension.class)
@BotWith(FooBotInitializer.class)
public class FooTest {
  // Use the name to identify your defined Pojo.
  @Bot("defaultFoo")
  private Foo foo;
  
  @Test
  public void should_get_foo() {
    assertThat(foo.getField1(), is("foo"));
  }
}
```

The initialized field can be customized. You can modify a specific field with new value.

```java
@ExtendWith(BotExtension.class)
@BotWith(FooBotInitializer.class)
public class ModifiedFooTest {
  @Bot(value = "defaultFoo")
  // Customize field field2 with value blah 
  @StringField(name = "field2", value="blah")
  private Foo foo;
  
  @Test
  public void should_get_foo() {
    assertThat(foo.getField2(), is("blah"));
  }
}
```

