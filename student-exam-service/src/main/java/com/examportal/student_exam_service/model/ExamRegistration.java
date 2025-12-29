package com.examportal.student_exam_service.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "exam_registrations")
public class ExamRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long examId;
    private String capId;  

    private LocalDate registeredAt;
}

