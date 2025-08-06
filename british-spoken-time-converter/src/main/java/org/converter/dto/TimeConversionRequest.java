package org.converter.dto;

import jakarta.validation.constraints.NotBlank;
import org.converter.utils.FormatType;

public class TimeConversionRequest {
    
    @NotBlank(message = "Time cannot be empty")
    private String time;
    
    private FormatType formatType = FormatType.BRITISH; // default value
    
    public TimeConversionRequest() {}
    
    public TimeConversionRequest(String time) {
        this.time = time;
        this.formatType = FormatType.BRITISH; // default value
    }
    
    public TimeConversionRequest(String time, FormatType formatType) {
        this.time = time;
        this.formatType = formatType;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public FormatType getFormatType() {
        return formatType;
    }
    
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }
} 