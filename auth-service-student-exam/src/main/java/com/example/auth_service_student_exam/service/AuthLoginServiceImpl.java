package com.example.auth_service_student_exam.service;

import com.example.auth_service_student_exam.dto.LoginRequest;
import com.example.auth_service_student_exam.dto.LoginResponse;
import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;
import com.example.auth_service_student_exam.exception.exception.auth.UserNotVerifiedException;
import com.example.auth_service_student_exam.exception.exception.login.InvalidCredentialsException;
import com.example.auth_service_student_exam.exception.exception.login.KeycloakException;
import com.example.auth_service_student_exam.model.AuthUser;
import com.example.auth_service_student_exam.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("authLoginServiceImpl")
@RequiredArgsConstructor
public class AuthLoginServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public LoginResponse login(LoginRequest request) {


        AuthUser user = authUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotVerifiedException("Email not registered"));

        if (!user.isVerified()) {
            throw new UserNotVerifiedException("Email not verified");
        }

        String tokenUrl = keycloakUrl + "/protocol/openid-connect/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", request.getEmail());
        body.add("password", request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    httpRequest,
                    Map.class
            );

            Map<String, Object> tokenResponse = response.getBody();
            return new LoginResponse(
                    (String) tokenResponse.get("access_token"),
                    (String) tokenResponse.get("refresh_token"),
                    ((Number) tokenResponse.get("expires_in")).longValue(),
                    "Bearer"
            );

        } catch (HttpClientErrorException.Unauthorized ex) {

            throw new InvalidCredentialsException("Invalid email or password");
        } catch (HttpClientErrorException ex) {

            throw new KeycloakException("Keycloak error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new KeycloakException("Unexpected error during login: " + ex.getMessage());
        }
    }

    @Override
    public boolean verifyCapId(String capId) { throw new UnsupportedOperationException(); }

    @Override
    public boolean sendOtp(String email, String capId) { throw new UnsupportedOperationException(); }

    @Override
    public boolean verifyOtp(String email, String otp) { throw new UnsupportedOperationException(); }

    @Override
    public boolean resendOtp(String email) { throw new UnsupportedOperationException(); }

    @Override
    public SignupResponse signup(SignupRequest request) { throw new UnsupportedOperationException(); }

    @Override
    public boolean sendPasswordResetOtp(String email) { throw new UnsupportedOperationException(); }

    @Override
    public boolean verifyPasswordResetOtp(String email, String otp) { throw new UnsupportedOperationException(); }

    @Override
    public boolean resetPassword(String email, String newPassword) { throw new UnsupportedOperationException(); }

    @Override
    public boolean resendPasswordResetOtp(String email) { throw new UnsupportedOperationException(); }
}
