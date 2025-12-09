package com.example.auth_service_student_exam.exception.exception.passwordreset;

public class OtpGenerationFailedException extends RuntimeException {
    public OtpGenerationFailedException(String message) {
        super(message);
    }
}
