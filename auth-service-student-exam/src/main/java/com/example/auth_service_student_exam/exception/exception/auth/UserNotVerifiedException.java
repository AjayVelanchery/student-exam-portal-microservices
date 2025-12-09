package com.example.auth_service_student_exam.exception.exception.auth;

public class UserNotVerifiedException extends AuthServiceException {
    public UserNotVerifiedException(String message) {
        super(message, 400);
    }
}
