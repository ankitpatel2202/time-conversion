package org.converter.service.impl;

import org.converter.service.api.CzechTimeConverter;
import org.converter.service.strategy.impl.CzechSpokenTimeStrategy;
import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CzechTimeConverterImpl implements CzechTimeConverter {
    private final TimeConversionStrategy strategy;

    @Autowired
    public CzechTimeConverterImpl(CzechSpokenTimeStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String convertToCzechSpoken(String timeString) {
        return strategy.convert(timeString);
    }
}
