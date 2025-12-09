package com.example.auth_service_student_exam.mapper;

import com.example.auth_service_student_exam.dto.SignupRequest;
import com.example.auth_service_student_exam.dto.SignupResponse;
import com.example.auth_service_student_exam.model.AuthUser;
import org.springframework.stereotype.Component;

@Component
public class AuthUserMapper {


    public AuthUser toEntity(AuthUser existingUser, String keycloakId) {
        existingUser.setKeycloakId(keycloakId);
        return existingUser;
    }


    public SignupResponse toResponse(AuthUser user) {
        return new SignupResponse(user.getKeycloakId(), "Student registered successfully");
    }
}
