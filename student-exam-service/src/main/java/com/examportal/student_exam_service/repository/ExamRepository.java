package com.examportal.student_exam_service.repository;

import com.examportal.student_exam_service.model.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<ExamRegistration,Long> {
}
