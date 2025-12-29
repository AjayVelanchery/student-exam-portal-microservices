package com.example.student_portal.student_service.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String errorCode, String field, String message) {
        super(message);
    }
}
