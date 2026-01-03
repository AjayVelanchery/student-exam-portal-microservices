package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.InternalMarkRequestDTO;
import com.examportal.student_exam_service.dto.InternalMarkResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InternalMarkService {


    InternalMarkResponseDTO addInternalMark(
            Long examId,
            InternalMarkRequestDTO requestDTO
    );


    void uploadInternalMarksFromCsv(
            Long examId,
            MultipartFile file
    );

    List<InternalMarkResponseDTO> getMyInternalMarks(String capId);
    InternalMarkResponseDTO updateInternalMark(
            Long id,
            InternalMarkRequestDTO requestDTO
    );

    void deleteInternalMark(Long id);

}
