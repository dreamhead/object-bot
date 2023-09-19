package com.github.dreamhead.bot;

public class BigData {
    private Data data;
    private int intValue;
    private double doubleValue;

    private long longValue;

    private float floatValue;

    private char charValue;

    private short shortValue;

    private byte byteValue;

    private boolean booleanValue;

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

    public char getCharValue() {
        return charValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }
}
