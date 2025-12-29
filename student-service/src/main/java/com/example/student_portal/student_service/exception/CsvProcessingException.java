package com.example.student_portal.student_service.exception;

public class CsvProcessingException extends RuntimeException {

    public CsvProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
