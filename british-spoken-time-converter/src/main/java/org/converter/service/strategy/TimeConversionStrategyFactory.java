package org.converter.service.strategy;

import java.util.Map;

import org.converter.service.strategy.api.TimeConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeConversionStrategyFactory {
    private final Map<String, TimeConversionStrategy> strategies;

    @Autowired
    public TimeConversionStrategyFactory(Map<String, TimeConversionStrategy> strategies) {
        this.strategies = strategies;
    }

    public TimeConversionStrategy getStrategy(String key) {
        TimeConversionStrategy strategy = strategies.get(key);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for key: " + key);
        }
        return strategy;
    }
}
