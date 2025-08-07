package org.converter.service.strategy.impl;

import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.stereotype.Component;

@Component
public class GermanSpokenTimeStrategy implements TimeConversionStrategy {
    @Override
    public String convert(String timeString) {
        // TODO: Implement German spoken time conversion logic
        throw new UnsupportedOperationException("German spoken time conversion not implemented yet");
    }
}
