package com.github.dreamhead.bot;

import java.time.ZonedDateTime;

public class ZonedDateTimeData {
    private ZonedDateTime field1;
    private String field2;

    public ZonedDateTimeData(final ZonedDateTime field1, final String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public ZonedDateTime getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }
}
