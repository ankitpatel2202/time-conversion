package org.converter.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.converter.service.strategy.impl.BritishSpokenTimeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BritishSpokenTimeStrategyTest {
    private BritishSpokenTimeStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new BritishSpokenTimeStrategy();
    }

    @Test
    void testNoon() {
        assertEquals("noon", strategy.convert("12:00"));
    }

    @Test
    void testMidnight() {
        assertEquals("midnight", strategy.convert("00:00"));
    }

    @Test
    void testHalfPast() {
        assertEquals("half past seven am", strategy.convert("07:30"));
        assertEquals("half past two pm", strategy.convert("14:30"));
    }

    @Test
    void testQuarter() {
        assertEquals("quarter past nine am", strategy.convert("09:15"));
        assertEquals("quarter past four am", strategy.convert("04:15"));
        assertEquals("quarter past three pm", strategy.convert("15:15"));
        assertEquals("quarter to five pm", strategy.convert("16:45"));
        assertEquals("quarter to twelve am", strategy.convert("23:45"));
    }

    @Test
    void testRandom() {
        assertEquals("twenty past seven am", strategy.convert("07:20"));
        assertEquals("five past three am", strategy.convert("03:05"));
        assertEquals("twenty past five am", strategy.convert("05:20"));
        assertEquals("twenty five past six am", strategy.convert("06:25"));
        assertEquals("twenty five to eight am", strategy.convert("07:35"));
        assertEquals("twenty to nine am", strategy.convert("08:40"));
        assertEquals("ten to eleven am", strategy.convert("10:50"));
        assertEquals("five to twelve pm", strategy.convert("11:55"));
        assertEquals("ten to twelve am", strategy.convert("23:50"));
        assertEquals("one o'clock pm", strategy.convert("13:00"));
        assertEquals("six o'clock am", strategy.convert("06:00"));
        assertEquals("one to twelve am", strategy.convert("23:59"));
        assertEquals("one past twelve am", strategy.convert("00:01"));
        assertEquals("one o'clock am", strategy.convert("01:00"));
        assertEquals("five past two am", strategy.convert("02:05"));
        assertEquals("ten past three am", strategy.convert("03:10"));
    }

    @Test
    void testInvalidTimeFormat() {
        assertThrows(IllegalArgumentException.class, () -> strategy.convert("25:00"));
        assertThrows(IllegalArgumentException.class, () -> strategy.convert("12:60"));
        assertThrows(IllegalArgumentException.class, () -> strategy.convert("invalid"));
    }

    @Test
    void testNullAndEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> strategy.convert(null));
        assertThrows(IllegalArgumentException.class, () -> strategy.convert(""));
        assertThrows(IllegalArgumentException.class, () -> strategy.convert("   "));
    }
}
