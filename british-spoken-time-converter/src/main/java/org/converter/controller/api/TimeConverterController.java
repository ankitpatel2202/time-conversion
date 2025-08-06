package org.converter.controller.api;

import org.converter.dto.TimeConversionRequest;
import org.converter.dto.TimeConversionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


public interface TimeConverterController {

    @PostMapping("/api/v1/time/convert")
    ResponseEntity<TimeConversionResponse> convertTime(@Valid @RequestBody TimeConversionRequest request);

} 