package com.examportal.student_exam_service.repository;

import com.examportal.student_exam_service.model.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration,Long> {

    List<ExamRegistration> findByCapId(String capId);

    boolean existsByExamIdAndCapId(Long examId, String capId);
}
