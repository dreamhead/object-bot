package com.github.dreamhead.bot;

public class SubData extends BaseData {
    private String field3;

    public SubData(final String field1,
                   final String field2, final String field3) {
        super(field1, field2);
        this.field3 = field3;
    }

    public String getField3() {
        return field3;
    }
}
