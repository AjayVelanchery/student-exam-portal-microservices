package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.ExamRequestDTO;
import com.examportal.student_exam_service.dto.ExamResponseDTO;
import com.examportal.student_exam_service.mapper.ExamMapper;
import com.examportal.student_exam_service.model.Exam;
import com.examportal.student_exam_service.repository.ExamRepository;
import com.examportal.student_exam_service.service.ExamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public ExamResponseDTO createExam(ExamRequestDTO requestDTO) {
        Exam exam = ExamMapper.toEntity(requestDTO);
        Exam savedExam = examRepository.save(exam);
        return ExamMapper.toDTO(savedExam);
    }

    @Override
    public ExamResponseDTO getExamById(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exam not found with id: " + examId)
                );
        return ExamMapper.toDTO(exam);
    }

    @Override
    public List<ExamResponseDTO> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(ExamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamResponseDTO updateExam(Long examId, ExamRequestDTO requestDTO) {

        Exam existingExam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exam not found with id: " + examId)
                );


        ExamMapper.updateEntity(existingExam, requestDTO);

        Exam updatedExam = examRepository.save(existingExam);

        return ExamMapper.toDTO(updatedExam);
    }


    @Override
    public void deleteExam(Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exam not found with id: " + examId)
                );

        examRepository.delete(exam);
    }

}
