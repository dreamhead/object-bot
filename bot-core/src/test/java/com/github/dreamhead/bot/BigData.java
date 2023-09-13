package com.github.dreamhead.bot;

public class BigData {
    private Data data;
    private int intValue;

    private double doubleValue;

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
}
