package com.examportal.student_exam_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExamRegistrationResponseDTO {
    private Long id;
    private Long examId;
    private String capId;
    private LocalDate registeredAt;
}
