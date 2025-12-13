package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import com.example.student_portal.student_service.dto.StudentProfileResponse;
import com.example.student_portal.student_service.mapper.StudentPreRegisterMapper;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentSelfServiceImpl implements StudentSelfService {

    private final StudentPreRegisterRepository repository;

    @Override
    public StudentProfileResponse getMyProfile(String capId) {
        StudentPreRegister entity = repository.findByCapId(capId)
                .orElseThrow(() -> new RuntimeException("Student profile not found for logged-in user"));
        return StudentPreRegisterMapper.toProfileResponse(entity); // full profile
    }

    @Override
    @Transactional
    public StudentProfileResponse updateMyProfile(String capId, StudentPreRegisterRequest request) {
        StudentPreRegister entity = repository.findByCapId(capId)
                .orElseThrow(() -> new RuntimeException("Student profile not found for logged-in user"));

        StudentPreRegisterMapper.updateEntity(entity, request);
        StudentPreRegister updated = repository.save(entity);

        return StudentPreRegisterMapper.toProfileResponse(updated); // full profile
    }
}

