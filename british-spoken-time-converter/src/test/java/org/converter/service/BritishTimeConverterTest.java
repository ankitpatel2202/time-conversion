package org.converter.service;

import org.converter.service.api.BritishTimeConverter;
import org.converter.service.impl.BritishTimeConverterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BritishTimeConverterTest {

    private BritishTimeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BritishTimeConverterImpl();
    }

    @Test
    void testNoon() {
        assertEquals("noon", converter.convertToBritishSpoken("12:00"));
    }

    @Test
    void testMidnight() {
        assertEquals("midnight", converter.convertToBritishSpoken("00:00"));
    }

    @Test
    void testHalfPast() {
        assertEquals("half past seven am", converter.convertToBritishSpoken("07:30"));
        assertEquals("half past two pm", converter.convertToBritishSpoken("14:30"));
    }

    @Test
    void testQuarter() {
        assertEquals("quarter past nine am", converter.convertToBritishSpoken("09:15"));
        assertEquals("quarter past four am", converter.convertToBritishSpoken("04:15"));
        assertEquals("quarter past three pm", converter.convertToBritishSpoken("15:15"));
        assertEquals("quarter to five pm", converter.convertToBritishSpoken("16:45"));
        assertEquals("quarter to twelve am", converter.convertToBritishSpoken("23:45"));
    }

    @Test
    void testRandom() {
        assertEquals("twenty past seven am", converter.convertToBritishSpoken("07:20"));
        assertEquals("five past three am", converter.convertToBritishSpoken("03:05"));
        assertEquals("twenty past five am", converter.convertToBritishSpoken("05:20"));
        assertEquals("twenty five past six am", converter.convertToBritishSpoken("06:25"));

        //assertEquals("six thirty two am", converter.convertToBritishSpoken("06:32")); //TODO: need more clarification

        assertEquals("twenty five to eight am", converter.convertToBritishSpoken("07:35"));

        assertEquals("twenty to nine am", converter.convertToBritishSpoken("08:40"));
        assertEquals("ten to eleven am", converter.convertToBritishSpoken("10:50"));
        assertEquals("five to twelve pm", converter.convertToBritishSpoken("11:55"));
        assertEquals("ten to twelve am", converter.convertToBritishSpoken("23:50"));
        assertEquals("one o'clock pm", converter.convertToBritishSpoken("13:00"));
        assertEquals("six o'clock am", converter.convertToBritishSpoken("06:00"));
        assertEquals("one to twelve am", converter.convertToBritishSpoken("23:59"));
        assertEquals("one past twelve am", converter.convertToBritishSpoken("00:01"));
        assertEquals("one o'clock am", converter.convertToBritishSpoken("01:00"));
        assertEquals("five past two am", converter.convertToBritishSpoken("02:05"));
        assertEquals("ten past three am", converter.convertToBritishSpoken("03:10"));

    }

    @Test
    void testInvalidTimeFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken("25:00");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken("12:60");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken("invalid");
        });
    }

    @Test
    void testNullAndEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken(null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken("");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToBritishSpoken("   ");
        });
    }
} 