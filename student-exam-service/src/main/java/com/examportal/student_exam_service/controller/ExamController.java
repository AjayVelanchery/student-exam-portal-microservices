package com.examportal.student_exam_service.controller;

import com.examportal.student_exam_service.dto.ExamRequestDTO;
import com.examportal.student_exam_service.dto.ExamResponseDTO;
import com.examportal.student_exam_service.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;


    @PostMapping
    public ResponseEntity<ExamResponseDTO> createExam(
            @Valid @RequestBody ExamRequestDTO requestDTO) {

        ExamResponseDTO response = examService.createExam(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{examId}")
    public ResponseEntity<ExamResponseDTO> getExamById(
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                examService.getExamById(examId)
        );
    }


    @GetMapping
    public ResponseEntity<List<ExamResponseDTO>> getAllExams() {

        return ResponseEntity.ok(
                examService.getAllExams()
        );
    }


    @PutMapping("/{examId}")
    public ResponseEntity<ExamResponseDTO> updateExam(
            @PathVariable Long examId,
            @Valid   @RequestBody ExamRequestDTO requestDTO) {

        return ResponseEntity.ok(
                examService.updateExam(examId, requestDTO)
        );
    }


    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
            @PathVariable Long examId) {

        examService.deleteExam(examId);
        return ResponseEntity.noContent().build(); // 204
    }
}
