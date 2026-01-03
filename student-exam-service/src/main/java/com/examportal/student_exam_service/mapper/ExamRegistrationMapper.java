package com.examportal.student_exam_service.mapper;

import com.examportal.student_exam_service.dto.ExamRegistrationRequestDTO;
import com.examportal.student_exam_service.dto.ExamRegistrationResponseDTO;
import com.examportal.student_exam_service.model.ExamRegistration;

import java.time.LocalDate;

public class ExamRegistrationMapper {

    public static ExamRegistration toEntity(
            ExamRegistrationRequestDTO dto,
            String capId
    ) {
        ExamRegistration registration = new ExamRegistration();
        registration.setExamId(dto.getExamId());
        registration.setCapId(capId);
        registration.setRegisteredAt(LocalDate.now());
        return registration;
    }

    public static ExamRegistrationResponseDTO toDTO(
            ExamRegistration registration
    ) {
        ExamRegistrationResponseDTO dto = new ExamRegistrationResponseDTO();
        dto.setId(registration.getId());
        dto.setExamId(registration.getExamId());
        dto.setCapId(registration.getCapId());
        dto.setRegisteredAt(registration.getRegisteredAt());
        return dto;
    }
}
