package com.examportal.student_exam_service.mapper;

import com.examportal.student_exam_service.dto.ExamRequestDTO;
import com.examportal.student_exam_service.dto.ExamResponseDTO;
import com.examportal.student_exam_service.model.Exam;

public class ExamMapper {

    public static Exam toEntity(ExamRequestDTO dto) {
        Exam exam = new Exam();
        exam.setName(dto.getName());
        exam.setCourse(dto.getCourse());
        exam.setSemester(dto.getSemester());
        exam.setStartDate(dto.getStartDate());
        exam.setEndDate(dto.getEndDate());

        return exam;

    }

    public static ExamResponseDTO toDTO(Exam exam) {
        ExamResponseDTO dto = new ExamResponseDTO();
        dto.setId(exam.getId());
        dto.setName(exam.getName());
        dto.setCourse(exam.getCourse());
        dto.setSemester(exam.getSemester());
        dto.setStartDate(exam.getStartDate());
        dto.setEndDate(exam.getEndDate());

        return dto;
    }

    public static void updateEntity(Exam exam, ExamRequestDTO dto) {
        exam.setName(dto.getName());
        exam.setCourse(dto.getCourse());
        exam.setSemester(dto.getSemester());
        exam.setStartDate(dto.getStartDate());
        exam.setEndDate(dto.getEndDate());
    }
}