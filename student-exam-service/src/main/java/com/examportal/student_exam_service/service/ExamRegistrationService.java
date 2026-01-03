package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.ExamRegistrationRequestDTO;
import com.examportal.student_exam_service.dto.ExamRegistrationResponseDTO;

public interface ExamRegistrationService {

    ExamRegistrationResponseDTO registerForExam(
            ExamRegistrationRequestDTO requestDTO,
            String capId
    );
}
