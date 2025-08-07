package org.converter.service.strategy.impl;

import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.stereotype.Component;

@Component
public class CzechSpokenTimeStrategy implements TimeConversionStrategy {
    @Override
    public String convert(String timeString) {
        // TODO: Implement Czech spoken time conversion logic
        throw new UnsupportedOperationException("Czech spoken time conversion not implemented yet");
    }
}
