package com.example.auth_service_student_exam.controller;

import com.example.auth_service_student_exam.dto.*;
import com.example.auth_service_student_exam.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    public AuthController(@Qualifier("authSignupServiceImpl") AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/verify-capid")
    public ResponseEntity<CapIdResponse> verifyCapId(
            @Valid @RequestBody CapIdRequest request) {

        boolean valid = authService.verifyCapId(request.getCapId());

        CapIdResponse response = new CapIdResponse(
                request.getCapId(),
                valid,
                valid ? "CAP ID is valid" : "CAP ID not found"
        );

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpSendRequest request) {
        authService.sendOtp(request.getEmail(), request.getCapId());
        return ResponseEntity.ok("OTP sent to email");
    }



    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequest request) {
        boolean success = authService.verifyOtp(request.getEmail(), request.getOtp());
        if (!success) return ResponseEntity.status(400).body("Invalid OTP");
        return ResponseEntity.ok("Email verified successfully");
    }



    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        authService.resendOtp(email);
        return ResponseEntity.ok("OTP resent successfully");
    }
}
