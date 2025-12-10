package com.example.auth_service_student_exam.exception.exception.login;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
