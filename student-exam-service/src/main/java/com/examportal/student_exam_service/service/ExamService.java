package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.ExamRequestDTO;
import com.examportal.student_exam_service.dto.ExamResponseDTO;

import java.util.List;

public interface ExamService {

    ExamResponseDTO createExam(ExamRequestDTO requestDTO);

    ExamResponseDTO getExamById(Long examId);

    List<ExamResponseDTO> getAllExams();


    ExamResponseDTO updateExam(Long examId, ExamRequestDTO requestDTO);


    void deleteExam(Long examId);

}
