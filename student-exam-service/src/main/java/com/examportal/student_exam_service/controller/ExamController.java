package com.examportal.student_exam_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ExamController {

    @GetMapping("/test")
    public String test() {
        return "Exam Service is working!";
    }
}
