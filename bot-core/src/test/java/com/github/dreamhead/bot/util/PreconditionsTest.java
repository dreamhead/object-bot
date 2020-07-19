package com.github.dreamhead.bot.util;

import org.junit.jupiter.api.Test;

import static com.github.dreamhead.bot.util.Preconditions.checkNotNullElements;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PreconditionsTest {
    @Test
    public void should_check_no_null_elements() {
        String[] nullElements = new String[1];
        assertThrows(NullPointerException.class, () -> checkNotNullElements(nullElements, ""));

        String[] notNullElements = new String[1];
        notNullElements[0] = "123";
        String[] result = checkNotNullElements(notNullElements, "");
        assertThat(result[0]).isEqualTo("123");

        String[] nullArray = null;
        assertThat(checkNotNullElements(null, "")).isNull();
    }
}