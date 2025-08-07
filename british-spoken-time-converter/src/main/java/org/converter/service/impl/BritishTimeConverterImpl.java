package org.converter.service.impl;

import org.converter.service.api.BritishTimeConverter;
import org.converter.service.strategy.impl.BritishSpokenTimeStrategy;
import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BritishTimeConverterImpl implements BritishTimeConverter {
    private final TimeConversionStrategy strategy;

    @Autowired
    public BritishTimeConverterImpl(BritishSpokenTimeStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String convertToBritishSpoken(String timeString) {
        return strategy.convert(timeString);
    }
}
