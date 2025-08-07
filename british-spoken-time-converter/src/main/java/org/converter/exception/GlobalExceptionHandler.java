package org.converter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        CustomErrorResponse error = new CustomErrorResponse(
            ex.getStatusCode().value(),
            ex.getReason(),
            ex.getCause() != null ? ex.getCause().getMessage() : null
        );
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        CustomErrorResponse error = new CustomErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid request body",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleNotReadableException(HttpMessageNotReadableException ex) {
        CustomErrorResponse error = new CustomErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Malformed JSON request",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    public static class CustomErrorResponse {
        private int status;
        private String error;
        private String details;

        public CustomErrorResponse(int status, String error, String details) {
            this.status = status;
            this.error = error;
            this.details = details;
        }

        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getDetails() { return details; }

        public void setStatus(int status) { this.status = status; }
        public void setError(String error) { this.error = error; }
        public void setDetails(String details) { this.details = details; }
    }
}
