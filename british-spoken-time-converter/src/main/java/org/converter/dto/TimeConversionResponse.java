package org.converter.dto;

public class TimeConversionResponse {
    private String originalTime;
    private String spokenTime;
    private String error;
    
    public TimeConversionResponse() {}
    
    public TimeConversionResponse(String originalTime, String spokenTime, String error) {
        this.originalTime = originalTime;
        this.spokenTime = spokenTime;
        this.error = error;
    }
    
    public String getOriginalTime() {
        return originalTime;
    }
    
    public void setOriginalTime(String originalTime) {
        this.originalTime = originalTime;
    }
    
    public String getSpokenTime() {
        return spokenTime;
    }
    
    public void setSpokenTime(String spokenTime) {
        this.spokenTime = spokenTime;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
} 