package com.github.kevindagame;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeFormatterTest {
    @Test
    public void testFormatting(){
        var milliseconds = 1000;
        var result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("00:00:01", result);

        milliseconds = 60000;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("00:01:00", result);

        milliseconds = 600000;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("00:10:00", result);

        milliseconds = 3600000;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("01:00:00", result);

        milliseconds = 4339999;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("01:12:19", result);

        milliseconds = 86400000;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("1 day 00:00:00", result);

        milliseconds = 86400000 * 2;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("2 days 00:00:00", result);

        milliseconds = 90739999;
        result = TimeFormatter.formatTimeRemaining(milliseconds);
        assertEquals("1 day 01:12:19", result);
    }

    @org.junit.jupiter.api.Test
    void deformatTimeRemaining() {
        String input = "00:00:01";
        long output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(1000, output);

        input = "00:00:10";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(10000, output);

        input = "00:01:00";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(60000, output);

        input = "00:10:00";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(600000, output);

        input = "01:00:00";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(3600000, output);

        input = "10:00:00";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(36000000, output);

        input = "10:11:22";
        output = TimeFormatter.deformatTimeRemaining(input);
        assertEquals(36682000, output);

    }
}