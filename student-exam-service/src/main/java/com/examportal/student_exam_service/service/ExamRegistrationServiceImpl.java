package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.ExamRegistrationRequestDTO;
import com.examportal.student_exam_service.dto.ExamRegistrationResponseDTO;
import com.examportal.student_exam_service.mapper.ExamRegistrationMapper;
import com.examportal.student_exam_service.model.ExamRegistration;
import com.examportal.student_exam_service.repository.ExamRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamRegistrationServiceImpl implements ExamRegistrationService {

    private final ExamRegistrationRepository repository;

    @Override
    public ExamRegistrationResponseDTO registerForExam(
            ExamRegistrationRequestDTO requestDTO,
            String capId
    ) {


        if (capId == null || capId.isBlank()) {
            throw new IllegalArgumentException("CAP ID is required");
        }

        if (requestDTO.getExamId() == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }


        boolean alreadyRegistered =
                repository.existsByExamIdAndCapId(
                        requestDTO.getExamId(),
                        capId
                );

        if (alreadyRegistered) {
            throw new IllegalStateException("Already registered for this exam");
        }

        ExamRegistration registration =
                ExamRegistrationMapper.toEntity(requestDTO, capId);


        ExamRegistration saved = repository.save(registration);


        return ExamRegistrationMapper.toDTO(saved);
    }
}
