package org.converter.controller.impl;

import jakarta.validation.Valid;
import org.converter.controller.api.TimeConverterController;
import org.converter.dto.TimeConversionRequest;
import org.converter.dto.TimeConversionResponse;
import org.converter.service.api.BritishTimeConverter;
import org.converter.service.api.CzechTimeConverter;
import org.converter.service.api.GermanTimeConverter;
import org.converter.utils.FormatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TimeConverterControllerImpl implements TimeConverterController {

    private final BritishTimeConverter britishTimeConverter;
    private final GermanTimeConverter germanTimeConverter;
    private final CzechTimeConverter czechTimeConverter;

    @Autowired
    public TimeConverterControllerImpl(BritishTimeConverter britishTimeConverter,
                                     GermanTimeConverter germanTimeConverter,
                                     CzechTimeConverter czechTimeConverter) {
        this.britishTimeConverter = britishTimeConverter;
        this.germanTimeConverter = germanTimeConverter;
        this.czechTimeConverter = czechTimeConverter;
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
        switch (formatType) {
            case BRITISH:
                return britishTimeConverter.convertToBritishSpoken(timeString);
            case GERMAN:
                return germanTimeConverter.convertToGermanSpoken(timeString);
            case CZECH:
                return czechTimeConverter.convertToCzechSpoken(timeString);
            default:
                throw new IllegalArgumentException("Unsupported format type: " + formatType);
        }
    }
}
