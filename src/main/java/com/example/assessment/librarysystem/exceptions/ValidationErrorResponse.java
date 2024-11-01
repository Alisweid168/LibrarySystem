package com.example.assessment.librarysystem.exceptions;
import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private List<ValidationError> errors;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(LocalDateTime timestamp, List<ValidationError> errors) {
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}
