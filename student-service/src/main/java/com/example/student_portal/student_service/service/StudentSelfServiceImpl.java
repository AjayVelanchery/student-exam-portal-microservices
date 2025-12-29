package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentProfileResponse;
import com.example.student_portal.student_service.dto.StudentSelfUpdateRequest;
import com.example.student_portal.student_service.exception.BadRequestException;
import com.example.student_portal.student_service.exception.ResourceNotFoundException;
import com.example.student_portal.student_service.mapper.StudentPreRegisterMapper;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import com.example.student_portal.student_service.service.StudentSelfService;
import com.example.student_portal.student_service.validator.StudentSelfUpdateValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentSelfServiceImpl implements StudentSelfService {
    private final StudentSelfUpdateValidator validator;

    private final StudentPreRegisterRepository repository;

    @Override
    public StudentProfileResponse getMyProfile(String capId) {

        if (capId == null || capId.trim().isEmpty()) {
            throw new BadRequestException("X-Cap-Id header is required");
        }

        StudentPreRegister entity = repository.findByCapId(capId.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found for logged-in user"
                        ));

        return StudentPreRegisterMapper.toProfileResponse(entity);
    }

    @Override
    @Transactional
    public StudentProfileResponse updateMyProfile(
            String capId,
            StudentSelfUpdateRequest request) {

        if (capId == null || capId.trim().isEmpty()) {
            throw new BadRequestException("X-Cap-Id header is required");
        }

        validator.validate(request);
        StudentPreRegister entity = repository.findByCapId(capId.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found for logged-in user"
                        ));

        StudentPreRegisterMapper.updateEntityforStudent(entity, request);
        StudentPreRegister updated = repository.save(entity);

        return StudentPreRegisterMapper.toProfileResponse(updated);
    }
}
