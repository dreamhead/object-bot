package com.github.dreamhead.bot.junit5;

public class Data {
    private String field1;
    private String field2;
    private long longField;
    private int intField;

    public Data() {
    }

    public Data(final String field1, final String field2, final long longField, int intField) {
        this.field1 = field1;
        this.field2 = field2;
        this.longField = longField;
        this.intField = intField;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public long getLongField() {
        return longField;
    }

    public int getIntField() {
        return intField;
    }
}
