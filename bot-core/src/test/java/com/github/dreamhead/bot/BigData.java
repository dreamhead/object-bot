package com.github.dreamhead.bot;

public class BigData {
    private Data data;
    private int intValue;
    private double doubleValue;

    private long longValue;

    private float floatValue;

    public BigData(final Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }
}
