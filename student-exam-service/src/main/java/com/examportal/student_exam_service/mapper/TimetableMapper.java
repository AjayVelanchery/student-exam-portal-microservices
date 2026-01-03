package com.examportal.student_exam_service.mapper;

import com.examportal.student_exam_service.dto.SubjectExamSlotDTO;
import com.examportal.student_exam_service.dto.TimetableRequestDTO;
import com.examportal.student_exam_service.dto.TimetableResponseDTO;
import com.examportal.student_exam_service.model.Timetable;

public class TimetableMapper {

    public static Timetable toEntity(TimetableRequestDTO dto, Long examId) {
        Timetable t = new Timetable();
        t.setExamId(examId);
        t.setSubjectCode(dto.getSubjectCode());
        t.setSubjectName(dto.getSubjectName());
        t.setExamDate(dto.getExamDate());
        t.setStartTime(dto.getStartTime());
        t.setEndTime(dto.getEndTime());
        return t;
    }

    public static TimetableResponseDTO toResponse(Timetable t) {
        TimetableResponseDTO dto = new TimetableResponseDTO();
        dto.setId(t.getId());
        dto.setSubjectCode(t.getSubjectCode());
        dto.setSubjectName(t.getSubjectName());
        dto.setExamDate(t.getExamDate());
        dto.setStartTime(t.getStartTime());
        dto.setEndTime(t.getEndTime());
        return dto;
    }

    public static SubjectExamSlotDTO toSubjectSlot(Timetable t) {
        SubjectExamSlotDTO dto = new SubjectExamSlotDTO();
        dto.setSubjectCode(t.getSubjectCode());
        dto.setSubjectName(t.getSubjectName());
        dto.setStartTime(t.getStartTime());
        dto.setEndTime(t.getEndTime());
        return dto;
    }
}
