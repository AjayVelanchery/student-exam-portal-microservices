package com.examportal.student_exam_service.repository;

import com.examportal.student_exam_service.model.Exam;
import com.examportal.student_exam_service.model.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,Long> {
    List<Exam> findByCourseAndSemester(
            String course,
            Integer semester
    );


}
