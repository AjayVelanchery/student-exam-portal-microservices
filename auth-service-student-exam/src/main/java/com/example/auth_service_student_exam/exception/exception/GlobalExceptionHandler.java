package com.example.auth_service_student_exam.exception;

import com.example.auth_service_student_exam.exception.exception.auth.AuthServiceException;
import com.example.auth_service_student_exam.exception.exception.auth.UserNotVerifiedException;
import com.example.auth_service_student_exam.exception.exception.login.InvalidCredentialsException;
import com.example.auth_service_student_exam.exception.exception.login.KeycloakException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.InvalidOtpException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.OtpGenerationFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<Map<String, Object>> handleAuthServiceException(AuthServiceException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus()))
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", ex.getStatus(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotVerified(UserNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 403,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 401,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<Map<String, Object>> handleKeycloakException(KeycloakException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PasswordResetUserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(PasswordResetUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 400,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(OtpGenerationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleOtpGenerationError(OtpGenerationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOtp(InvalidOtpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 400,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PasswordResetFailedException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordResetFailed(PasswordResetFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Internal Server Error: " + ex.getMessage(),
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }
}
