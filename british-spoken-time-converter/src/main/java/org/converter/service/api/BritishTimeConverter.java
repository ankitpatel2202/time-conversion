package org.converter.service.api;

import org.springframework.stereotype.Service;

@Service
public interface BritishTimeConverter {

    String convertToBritishSpoken(String timeString);
} 