package com.example.student_portal.student_service.repository;

import com.example.student_portal.student_service.model.StudentPreRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentPreRegisterRepository extends JpaRepository<StudentPreRegister, Long> {
    Optional<StudentPreRegister> findByCapId(String capId);
    boolean existsByCapId(String capId);
}
