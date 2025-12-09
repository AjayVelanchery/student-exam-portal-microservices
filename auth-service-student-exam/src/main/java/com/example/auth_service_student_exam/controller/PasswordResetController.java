package com.example.auth_service_student_exam.controller;

import com.example.auth_service_student_exam.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/password-reset")

public class PasswordResetController {

    private final AuthService authService;

    public PasswordResetController(
            @Qualifier("passwordResetServiceImpl") AuthService authService
    ) {
        this.authService = authService;
    }
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendPasswordResetOtp(@RequestParam String email) {
        authService.sendPasswordResetOtp(email);
        return ResponseEntity.ok("Password reset OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyPasswordResetOtp(@RequestParam String email,
                                                         @RequestParam String otp) {
        boolean valid = authService.verifyPasswordResetOtp(email, otp);
        if (!valid) return ResponseEntity.status(400).body("Invalid OTP");
        return ResponseEntity.ok("OTP verified successfully");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String newPassword) {
        authService.resetPassword(email, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendPasswordResetOtp(@RequestParam String email) {
        authService.resendPasswordResetOtp(email);
        return ResponseEntity.ok("Password reset OTP resent successfully");
    }
}
