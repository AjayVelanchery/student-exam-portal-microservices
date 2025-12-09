package com.example.auth_service_student_exam.service;

import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;

import com.example.auth_service_student_exam.exception.exception.passwordreset.InvalidOtpException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.OtpGenerationFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetFailedException;
import com.example.auth_service_student_exam.exception.exception.passwordreset.PasswordResetUserNotFoundException;
import com.example.auth_service_student_exam.repository.AuthUserRepository;
import com.example.auth_service_student_exam.service.Email.EmailService;
import com.example.auth_service_student_exam.service.Email.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;

@Service("passwordResetServiceImpl")
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final OtpService otpService;
    private final EmailService emailService;
    private final Keycloak keycloakClient;

    private final String KEYCLOAK_REALM = "Student-Portal";

    // ---------------------------------------------------------------------
    // SEND PASSWORD RESET OTP
    // ---------------------------------------------------------------------
    @Override
    public boolean sendPasswordResetOtp(String email) {

        authUserRepository.findByEmail(email)
                .orElseThrow(() -> new PasswordResetUserNotFoundException("Email not registered"));

        try {
            String otp = otpService.generateOtp(email);

            emailService.sendEmail(
                    email,
                    "Your OTP for Password Reset",
                    "Your OTP is: " + otp + "\nValid for 5 minutes."
            );

        } catch (Exception e) {
            throw new OtpGenerationFailedException("Failed to generate or send OTP");
        }

        return true;
    }


    @Override
    public boolean verifyPasswordResetOtp(String email, String otp) {

        boolean valid = otpService.verifyOtp(email, otp);

        if (!valid) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }

        return true;
    }

    @Override
    @Transactional
    public boolean resetPassword(String email, String newPassword) {

        var users = keycloakClient.realm(KEYCLOAK_REALM).users().search(email);

        if (users.isEmpty()) {
            throw new PasswordResetUserNotFoundException("User not found in Keycloak");
        }

        String userId = users.get(0).getId();

        try {

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(newPassword);
            passwordCred.setTemporary(false);


            keycloakClient.realm(KEYCLOAK_REALM)
                    .users()
                    .get(userId)
                    .resetPassword(passwordCred);

            otpService.deleteOtp(email);

        } catch (Exception e) {
            throw new PasswordResetFailedException("Failed to reset password in Keycloak");
        }

        return true;
    }


    @Override
    public boolean resendPasswordResetOtp(String email) {

        otpService.deleteOtp(email);

        return sendPasswordResetOtp(email);
    }


    @Override public boolean verifyCapId(String capId) { throw new UnsupportedOperationException(); }
    @Override public boolean sendOtp(String email, String capId) { throw new UnsupportedOperationException(); }
    @Override public boolean verifyOtp(String email, String otp) { throw new UnsupportedOperationException(); }
    @Override public boolean resendOtp(String email) { throw new UnsupportedOperationException(); }
    @Override public SignupResponse signup(SignupRequest request) { throw new UnsupportedOperationException(); }
}
