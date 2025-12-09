package com.example.auth_service_student_exam.exception.exception.passwordreset;

public class PasswordResetUserNotFoundException extends RuntimeException {
    public PasswordResetUserNotFoundException(String message) {
        super(message);
    }
}
