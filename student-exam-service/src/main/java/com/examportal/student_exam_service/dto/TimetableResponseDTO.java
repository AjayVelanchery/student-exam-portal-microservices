package com.examportal.student_exam_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class TimetableResponseDTO {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private LocalDate examDate;
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", example = "10:00", pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", example = "10:00", pattern = "HH:mm")
    private LocalTime endTime;
}
