package com.example.auth_service_student_exam.controller;

import com.example.auth_service_student_exam.dto.LoginRequest;
import com.example.auth_service_student_exam.dto.LoginResponse;
import com.example.auth_service_student_exam.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthLoginController {

    private final AuthService authLoginService;

    public AuthLoginController(@Qualifier("authLoginServiceImpl") AuthService authLoginService) {
        this.authLoginService = authLoginService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authLoginService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
