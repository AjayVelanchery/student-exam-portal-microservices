package com.examportal.student_exam_service.repository;

import com.examportal.student_exam_service.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable,Long> {
    List<Timetable> findByExamId(Long examId);

}
