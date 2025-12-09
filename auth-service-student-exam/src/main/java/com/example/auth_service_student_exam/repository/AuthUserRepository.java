package com.example.auth_service_student_exam.repository;

import com.example.auth_service_student_exam.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByCapId(String capId);

    Optional<AuthUser> findByEmail(String email);

    Optional<AuthUser> findByKeycloakId(String keycloakId);
}
