package com.examportal.student_exam_service.repository;

import com.examportal.student_exam_service.model.InternalMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternalMarkRepository extends JpaRepository<InternalMark,Long> {

    List<InternalMark> findByExamIdAndCapId(Long examId, String capId);
    boolean existsByExamIdAndCapIdAndSubjectCode(
            Long examId,
            String capId,
            String subjectCode
    );

    List<InternalMark> findByCapId(String capId);
}
