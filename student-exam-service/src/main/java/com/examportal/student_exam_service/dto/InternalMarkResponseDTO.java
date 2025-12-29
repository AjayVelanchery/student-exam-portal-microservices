package com.examportal.student_exam_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalMarkResponseDTO {
    private Long id;
    private Long examId;
    private String capId;
    private String subjectCode;
    private Integer marks;
}
