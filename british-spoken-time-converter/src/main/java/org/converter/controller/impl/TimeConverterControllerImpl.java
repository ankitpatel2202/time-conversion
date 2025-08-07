package org.converter.controller.impl;

import org.converter.controller.api.TimeConverterController;
import org.converter.dto.TimeConversionRequest;
import org.converter.dto.TimeConversionResponse;
import org.converter.service.strategy.api.TimeConversionStrategy;
import org.converter.service.strategy.TimeConversionStrategyFactory;
import org.converter.utils.FormatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class TimeConverterControllerImpl implements TimeConverterController {

    private final TimeConversionStrategyFactory strategyFactory;

    @Autowired
    public TimeConverterControllerImpl(TimeConversionStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public ResponseEntity<TimeConversionResponse> convertTime(@Valid @RequestBody TimeConversionRequest request) {
        try {
            String spokenTime = convertTimeBasedOnFormat(request.getTime(), request.getFormatType());
            return ResponseEntity.ok(new TimeConversionResponse(request.getTime(), spokenTime, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new TimeConversionResponse(request.getTime(), null, e.getMessage()));
        }
    }

    private String convertTimeBasedOnFormat(String timeString, FormatType formatType) {
        String strategyKey = getStrategyKey(formatType);
        TimeConversionStrategy strategy = strategyFactory.getStrategy(strategyKey);
        return strategy.convert(timeString);
    }

    private String getStrategyKey(FormatType formatType) {
        switch (formatType) {
            case BRITISH:
                return "britishSpokenTimeStrategy";
            case GERMAN:
                return "germanSpokenTimeStrategy";
            case CZECH:
                return "czechSpokenTimeStrategy";
            default:
                throw new IllegalArgumentException("Unsupported format type: " + formatType);
        }
    }
}
