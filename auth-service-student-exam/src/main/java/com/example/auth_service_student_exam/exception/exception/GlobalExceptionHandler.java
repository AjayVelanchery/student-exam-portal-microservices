package com.example.auth_service_student_exam.exception;

import com.example.auth_service_student_exam.exception.exception.auth.AuthServiceException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.InvalidOtpException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.OtpGenerationFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<Map<String, Object>> handleAuthServiceException(AuthServiceException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 500);
        body.put("error", "Internal Server Error: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(PasswordResetUserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(PasswordResetUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(OtpGenerationFailedException.class)
    public ResponseEntity<String> handleOtpError(OtpGenerationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<String> handleInvalidOtp(InvalidOtpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PasswordResetFailedException.class)
    public ResponseEntity<String> handleResetError(PasswordResetFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
