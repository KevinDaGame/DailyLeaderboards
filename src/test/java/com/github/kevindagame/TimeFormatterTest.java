package com.github.kevindagame;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeFormatterTest {
    @Test
    public void testFormatting(){
        var miliseconds = 1000;
        var result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("00:00:01", result);

        miliseconds = 60000;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("00:01:00", result);

        miliseconds = 600000;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("00:10:00", result);

        miliseconds = 3600000;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("01:00:00", result);

        miliseconds = 4339999;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("01:12:19", result);

        miliseconds = 86400000;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("1 day 00:00:00", result);

        miliseconds = 86400000 * 2;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
        assertEquals("2 days 00:00:00", result);

        miliseconds = 90739999;
        result = TimeFormatter.formatTimeRemaining(miliseconds);
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