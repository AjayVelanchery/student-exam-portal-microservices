package com.example.auth_service_student_exam.exception.exception.passwordreset;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
