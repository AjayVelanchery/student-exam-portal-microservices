package com.example.auth_service_student_exam.exception.exception.auth;

public class KeycloakUserCreationException extends AuthServiceException {
    public KeycloakUserCreationException(String message) {
        super(message, 500);
    }
}
