# Object Bot

Object bot is a library for setting up Java objects as test data, which is inspired by [Factory Bot](https://github.com/thoughtbot/factory_bot).

## Latest Release
**1.0.0**

More details in [Release Notes](bot-doc/ReleaseNotes.md).

## Object Bot in Your Build

To add a dependency on Object Bot using Maven, use the following:

```xml
<dependency>
  <groupId>com.github.dreamhead</groupId>
  <artifactId>bot-junit5</artifactId>
  <version>1.0.0</version>
</dependency>
```

To add a dependency using Gradle:

```groovy
dependencies {
  testImplementation(
    "com.github.dreamhead:bot-junit5:1.0.0",
  )
}
```

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

And then you could initialize all your test POJOs in an initializer. 

```java
import com.github.dreamhead.bot.annotation.BotInitializer;

public class FooBotInitializer implements BotInitializer {
    @Override
    public void initialize(final ObjectBot bot) {
        // Give a name to identify your Pojo.
        bot.define("defaultFoo", new Foo("foo", "bar"));
    }
}
```

Now you can use it in your test. Refer to the following Junit 5 example.

```java
// Run BotExtension
@ExtendWith(BotExtension.class)
// All test POJOs are initialized with FooBotInitializer. 
@BotWith(FooBotInitializer.class)
public class FooTest {
  // Use the name to identify your defined Pojo.
  // It will be injected for each test.
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

If the field customization only affects a single test, `override` API could be used.

```java
@ExtendWith(BotExtension.class)
@BotWith(FooBotInitializer.class)
public class FooTest {
  @Bot("defaultFoo")
  private Foo foo;
  
  @Test
  public void should_get_foo() {
    Foo newFoo = override(foo, field("field2").value("blah"));
    assertThat(newFoo.getField2(), is("blah"));
  }
}
