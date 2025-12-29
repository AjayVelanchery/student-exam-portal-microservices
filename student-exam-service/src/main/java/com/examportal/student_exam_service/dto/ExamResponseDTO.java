package com.examportal.student_exam_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ExamResponseDTO {
    private Long id;
    private String name;
    private String course;
    private Integer semester;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
