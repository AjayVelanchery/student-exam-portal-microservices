package com.example.auth_service_student_exam.service;

import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;

public interface AuthService {

    // ---------------- CAP ID Verification ----------------
    boolean verifyCapId(String capId);

    // ---------------- Signup ----------------
    SignupResponse signup(SignupRequest request);

    // ---------------- OTP for Email Verification ----------------
    boolean sendOtp(String email, String capId);  // updated to include capId
    boolean verifyOtp(String email, String otp);
    boolean resendOtp(String email);

    // ---------------- Password Reset ----------------
    boolean sendPasswordResetOtp(String email);
    boolean verifyPasswordResetOtp(String email, String otp);
    boolean resetPassword(String email, String newPassword);
    boolean resendPasswordResetOtp(String email);
}
