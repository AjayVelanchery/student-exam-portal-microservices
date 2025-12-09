package com.example.auth_service_student_exam.exception.exception.auth;

public class AuthServiceException extends RuntimeException {
    private final int status;

    public AuthServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
