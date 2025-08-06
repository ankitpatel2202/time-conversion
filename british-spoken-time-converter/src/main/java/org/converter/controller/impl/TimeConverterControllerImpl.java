package org.converter.controller.impl;


import jakarta.validation.Valid;
import org.converter.controller.api.TimeConverterController;
import org.converter.dto.TimeConversionRequest;
import org.converter.dto.TimeConversionResponse;
import org.converter.service.api.BritishTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TimeConverterControllerImpl implements TimeConverterController {

    private final BritishTimeConverter timeConverter;

    @Autowired
    public TimeConverterControllerImpl(BritishTimeConverter timeConverter) {
        this.timeConverter = timeConverter;
    }

    @Override
    public ResponseEntity<TimeConversionResponse> convertTime(@Valid @RequestBody TimeConversionRequest request) {
        try {
            String spokenTime = timeConverter.convertToBritishSpoken(request.getTime());
            return ResponseEntity.ok(new TimeConversionResponse(request.getTime(), spokenTime, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new TimeConversionResponse(request.getTime(), null, e.getMessage()));
        }
    }
}
