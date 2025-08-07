package org.converter.service.impl;

import org.converter.service.api.GermanTimeConverter;
import org.converter.service.strategy.impl.GermanSpokenTimeStrategy;
import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GermanTimeConverterImpl implements GermanTimeConverter {
    private final TimeConversionStrategy strategy;

    @Autowired
    public GermanTimeConverterImpl(GermanSpokenTimeStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String convertToGermanSpoken(String timeString) {
        return strategy.convert(timeString);
    }
}
