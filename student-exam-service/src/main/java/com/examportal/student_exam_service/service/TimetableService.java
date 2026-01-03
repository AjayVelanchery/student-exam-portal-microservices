package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.SemesterExamDateResponseDTO;
import com.examportal.student_exam_service.dto.TimetableRequestDTO;
import com.examportal.student_exam_service.dto.TimetableResponseDTO;

import java.util.List;

public interface TimetableService {

    TimetableResponseDTO addTimetable(
            Long examId,
            TimetableRequestDTO requestDTO
    );

    List<TimetableResponseDTO> getTimetableByExam(
            Long examId
    );
    List<SemesterExamDateResponseDTO> getExamDatesByCourseAndSemester(
            String course,
            Integer semester
    );
}
