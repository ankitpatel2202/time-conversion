package org.converter.service.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.converter.service.strategy.api.TimeConversionStrategy;
import org.converter.service.strategy.impl.BritishSpokenTimeStrategy;
import org.converter.service.strategy.impl.CzechSpokenTimeStrategy;
import org.converter.service.strategy.impl.GermanSpokenTimeStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeConversionStrategyFactoryTest {

    @Autowired
    private TimeConversionStrategyFactory factory;

    @Test
    void testGetBritishStrategy() {
        TimeConversionStrategy strategy = factory.getStrategy("britishSpokenTimeStrategy");
        assertNotNull(strategy);
        assertTrue(strategy instanceof BritishSpokenTimeStrategy);
    }

    @Test
    void testGetGermanStrategy() {
        TimeConversionStrategy strategy = factory.getStrategy("germanSpokenTimeStrategy");
        assertNotNull(strategy);
        assertTrue(strategy instanceof GermanSpokenTimeStrategy);
    }

    @Test
    void testGetCzechStrategy() {
        TimeConversionStrategy strategy = factory.getStrategy("czechSpokenTimeStrategy");
        assertNotNull(strategy);
        assertTrue(strategy instanceof CzechSpokenTimeStrategy);
    }

    @Test
    void testGetUnknownStrategyThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.getStrategy("unknownStrategy");
        });
    }
}
