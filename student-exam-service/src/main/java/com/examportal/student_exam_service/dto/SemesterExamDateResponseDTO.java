package com.examportal.student_exam_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SemesterExamDateResponseDTO {

    @Schema(
            type = "string",
            example = "2025-04-01",
            pattern = "yyyy-MM-dd"
    )
    private LocalDate examDate;

    private List<SubjectExamSlotDTO> subjects;
}

