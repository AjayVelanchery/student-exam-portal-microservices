package com.example.auth_service_student_exam.service;

import com.example.auth_service_student_exam.dto.LoginRequest;
import com.example.auth_service_student_exam.dto.LoginResponse;
import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;

public interface AuthService {

    boolean verifyCapId(String capId);

    SignupResponse signup(SignupRequest request);

    boolean sendOtp(String email, String capId);  // updated to include capId
    boolean verifyOtp(String email, String otp);
    boolean resendOtp(String email);

    boolean sendPasswordResetOtp(String email);
    boolean verifyPasswordResetOtp(String email, String otp);
    boolean resetPassword(String email, String newPassword);
    boolean resendPasswordResetOtp(String email);

    LoginResponse login(LoginRequest request);
}
