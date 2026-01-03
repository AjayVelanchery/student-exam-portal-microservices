package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.SemesterExamDateResponseDTO;
import com.examportal.student_exam_service.dto.SubjectExamSlotDTO;
import com.examportal.student_exam_service.dto.TimetableRequestDTO;
import com.examportal.student_exam_service.dto.TimetableResponseDTO;
import com.examportal.student_exam_service.mapper.TimetableMapper;
import com.examportal.student_exam_service.model.Exam;
import com.examportal.student_exam_service.model.Timetable;
import com.examportal.student_exam_service.repository.ExamRepository;
import com.examportal.student_exam_service.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository repository;
    private final ExamRepository examRepository;
    @Override
    public TimetableResponseDTO addTimetable(
            Long examId,
            TimetableRequestDTO requestDTO
    ) {

        if (examId == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }

        if (requestDTO.getSubjectCode() == null || requestDTO.getSubjectCode().isBlank()) {
            throw new IllegalArgumentException("Subject code is required");
        }

        boolean exists =
                repository.existsByExamIdAndSubjectCode(
                        examId,
                        requestDTO.getSubjectCode()
                );

        if (exists) {
            throw new IllegalStateException(
                    "Timetable already exists for this subject"
            );
        }

        Timetable timetable =
                TimetableMapper.toEntity(requestDTO, examId);

        Timetable saved = repository.save(timetable);

        return TimetableMapper.toResponse(saved);
    }

    @Override
    public List<TimetableResponseDTO> getTimetableByExam(
            Long examId
    ) {

        if (examId == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }

        return repository.findByExamId(examId)
                .stream()
                .map(TimetableMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<SemesterExamDateResponseDTO> getExamDatesByCourseAndSemester(
            String course,
            Integer semester
    ) {
        List<Exam> exams =
                examRepository.findByCourseAndSemester(course, semester);

        if (exams.isEmpty()) {
            return List.of();
        }

        List<Long> examIds =
                exams.stream()
                        .map(Exam::getId)
                        .toList();

        List<Timetable> timetables =
               repository
                        .findByExamIdInOrderByExamDateAscStartTimeAsc(examIds);

        Map<LocalDate, List<Timetable>> grouped =
                timetables.stream()
                        .collect(Collectors.groupingBy(
                                Timetable::getExamDate,
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        return grouped.entrySet()
                .stream()
                .map(entry -> {
                    SemesterExamDateResponseDTO dto =
                            new SemesterExamDateResponseDTO();
                    dto.setExamDate(entry.getKey());

                    List<SubjectExamSlotDTO> subjects =
                            entry.getValue().stream()
                                    .map(TimetableMapper::toSubjectSlot)
                                    .toList();

                    dto.setSubjects(subjects);
                    return dto;
                })
                .toList();
    }

}
