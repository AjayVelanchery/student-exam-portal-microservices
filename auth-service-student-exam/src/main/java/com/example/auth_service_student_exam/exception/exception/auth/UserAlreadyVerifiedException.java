package com.example.auth_service_student_exam.exception.exception.auth;

public class UserAlreadyVerifiedException extends AuthServiceException {
    public UserAlreadyVerifiedException(String message) {
        super(message, 400);
    }
}
