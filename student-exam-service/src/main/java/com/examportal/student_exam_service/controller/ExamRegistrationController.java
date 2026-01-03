package com.examportal.student_exam_service.controller;

import com.examportal.student_exam_service.dto.ExamRegistrationRequestDTO;
import com.examportal.student_exam_service.dto.ExamRegistrationResponseDTO;
import com.examportal.student_exam_service.service.ExamRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams/registrations")
@RequiredArgsConstructor
public class ExamRegistrationController {

    private final ExamRegistrationService service;

    @PostMapping
    public ResponseEntity<ExamRegistrationResponseDTO> registerForExam(
            @RequestHeader("X-Cap-Id") String capId,
            @RequestBody ExamRegistrationRequestDTO requestDTO
    ) {
        ExamRegistrationResponseDTO response =
                service.registerForExam(requestDTO, capId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
