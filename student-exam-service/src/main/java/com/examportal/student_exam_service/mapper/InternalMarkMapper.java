package com.examportal.student_exam_service.mapper;

import com.examportal.student_exam_service.dto.InternalMarkRequestDTO;
import com.examportal.student_exam_service.dto.InternalMarkResponseDTO;
import com.examportal.student_exam_service.model.InternalMark;

public class InternalMarkMapper {

    public static InternalMark toEntity(InternalMarkRequestDTO dto, Long examId) {
        InternalMark mark = new InternalMark();
        mark.setExamId(examId);
        mark.setCapId(dto.getCapId());
        mark.setSubjectCode(dto.getSubjectCode());
        mark.setMarks(dto.getMarks());
        return mark;
    }

    public static InternalMarkResponseDTO toResponse(InternalMark mark) {
        InternalMarkResponseDTO dto = new InternalMarkResponseDTO();
        dto.setId(mark.getId());
        dto.setExamId(mark.getExamId());
        dto.setCapId(mark.getCapId());
        dto.setSubjectCode(mark.getSubjectCode());
        dto.setMarks(mark.getMarks());
        return dto;
    }
}
