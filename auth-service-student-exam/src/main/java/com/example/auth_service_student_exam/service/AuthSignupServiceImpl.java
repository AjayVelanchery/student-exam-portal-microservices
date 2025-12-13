package com.example.auth_service_student_exam.service;

import com.example.auth_service_student_exam.dto.LoginRequest;
import com.example.auth_service_student_exam.dto.LoginResponse;
import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;
import com.example.auth_service_student_exam.exception.exception.auth.KeycloakUserCreationException;
import com.example.auth_service_student_exam.exception.exception.auth.UserAlreadyVerifiedException;
import com.example.auth_service_student_exam.exception.exception.auth.UserNotVerifiedException;
import com.example.auth_service_student_exam.mapper.AuthUserMapper;
import com.example.auth_service_student_exam.model.AuthUser;
import com.example.auth_service_student_exam.repository.AuthUserRepository;
import com.example.auth_service_student_exam.service.Email.EmailService;
import com.example.auth_service_student_exam.service.Email.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service("authSignupServiceImpl")
@RequiredArgsConstructor
public class AuthSignupServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final WebClient.Builder webClientBuilder;
    private final AuthUserMapper mapper;
    private final OtpService otpService;
    private final EmailService emailService;
    private final Keycloak keycloakClient;

    private final String STUDENT_SERVICE_URL = "http://localhost:8084/api/student/student";
    private final String KEYCLOAK_REALM = "Student-Portal";

    @Override
    public boolean verifyCapId(String capId) {
        Boolean exists = webClientBuilder.build()
                .get()
                .uri(STUDENT_SERVICE_URL + "/validate/{capId}", capId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(exists);
    }


    @Override
    public boolean sendOtp(String email, String capId) {
        AuthUser existing = authUserRepository.findByEmail(email).orElse(null);
        if (existing != null && existing.isVerified()) {
            throw new UserAlreadyVerifiedException("Email already verified");
        }

        if (existing == null) {
            AuthUser user = new AuthUser();
            user.setEmail(email);
            user.setCapId(capId);
            user.setVerified(false);
            authUserRepository.save(user);
        }

        String otp = otpService.generateOtp(email);
        emailService.sendEmail(email, "Your OTP for Email Verification",
                "Your OTP is: " + otp + "\nValid for 5 minutes.");

        return true;
    }

    @Override
    @Transactional
    public boolean verifyOtp(String email, String otp) {
        boolean valid = otpService.verifyOtp(email, otp);
        if (!valid) return false;

        authUserRepository.findByEmail(email).ifPresent(user -> {
            user.setVerified(true);
            authUserRepository.save(user);
        });

        otpService.deleteOtp(email);
        return true;
    }

    @Override
    public boolean resendOtp(String email) {
        otpService.deleteOtp(email);
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotVerifiedException("Email not registered"));
        return sendOtp(email, user.getCapId());
    }

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        AuthUser user = authUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotVerifiedException("Email not verified or not registered"));

        if (!user.isVerified()) {
            throw new UserNotVerifiedException("Email not verified");
        }

        var existingUsers = keycloakClient.realm(KEYCLOAK_REALM).users().search(request.getEmail());
        if (!existingUsers.isEmpty()) {
            throw new UserAlreadyVerifiedException("Email already registered in Keycloak");
        }

        String keycloakId = createKeycloakUser(request);
        user.setKeycloakId(keycloakId);
        authUserRepository.save(user);

        return mapper.toResponse(user);
    }

    private String createKeycloakUser(SignupRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());
        passwordCred.setTemporary(false);
        user.setCredentials(Collections.singletonList(passwordCred));

        var response = keycloakClient.realm(KEYCLOAK_REALM).users().create(user);
        if (response.getStatus() != 201) {
            String error = response.readEntity(String.class);
            throw new KeycloakUserCreationException(
                    "Failed to create user in Keycloak. Status: " + response.getStatus() + ", " + error);
        }

        String keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        var rolesResource = keycloakClient.realm(KEYCLOAK_REALM).roles();
        var studentRole = rolesResource.get("STUDENT").toRepresentation();
        keycloakClient.realm(KEYCLOAK_REALM)
                .users()
                .get(keycloakId)
                .roles()
                .realmLevel()
                .add(Collections.singletonList(studentRole));

        return keycloakId;
    }

    @Override
    public boolean sendPasswordResetOtp(String email) { throw new UnsupportedOperationException(); }

    @Override
    public boolean verifyPasswordResetOtp(String email, String otp) { throw new UnsupportedOperationException(); }

    @Override
    public boolean resetPassword(String email, String newPassword) { throw new UnsupportedOperationException(); }

    @Override
    public boolean resendPasswordResetOtp(String email) { throw new UnsupportedOperationException(); }


    public  LoginResponse login(LoginRequest request){throw new UnsupportedOperationException(); }


}
