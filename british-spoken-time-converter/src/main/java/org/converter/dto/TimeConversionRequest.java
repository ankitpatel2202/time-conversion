package org.converter.dto;

import jakarta.validation.constraints.NotBlank;

public class TimeConversionRequest {
    
    @NotBlank(message = "Time cannot be empty")
    private String time;
    
    public TimeConversionRequest() {}
    
    public TimeConversionRequest(String time) {
        this.time = time;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
} 