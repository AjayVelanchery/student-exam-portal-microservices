package com.examportal.student_exam_service.controller;

import com.examportal.student_exam_service.dto.SemesterExamDateResponseDTO;
import com.examportal.student_exam_service.dto.TimetableRequestDTO;
import com.examportal.student_exam_service.dto.TimetableResponseDTO;
import com.examportal.student_exam_service.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @PostMapping
    public TimetableResponseDTO addTimetable(
            @RequestParam Long examId,
            @RequestBody TimetableRequestDTO requestDTO
    ) {
        return timetableService.addTimetable(examId, requestDTO);
    }

    @GetMapping
    public List<TimetableResponseDTO> getTimetableByExam(
            @RequestParam Long examId
    ) {
        return timetableService.getTimetableByExam(examId);
    }

    @GetMapping("/semester")
    public List<SemesterExamDateResponseDTO> getSemesterExamDates(
            @RequestParam String course,
            @RequestParam Integer semester
    ) {
        return timetableService
                .getExamDatesByCourseAndSemester(course, semester);
    }



}
