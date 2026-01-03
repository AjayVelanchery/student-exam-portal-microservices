package com.examportal.student_exam_service.service;

import com.examportal.student_exam_service.dto.InternalMarkRequestDTO;
import com.examportal.student_exam_service.dto.InternalMarkResponseDTO;
import com.examportal.student_exam_service.exception.ResourceNotFoundException;
import com.examportal.student_exam_service.mapper.InternalMarkMapper;
import com.examportal.student_exam_service.model.InternalMark;
import com.examportal.student_exam_service.repository.InternalMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalMarkServiceImpl implements InternalMarkService {

    private final InternalMarkRepository repository;



    @Override
    public InternalMarkResponseDTO addInternalMark(
            Long examId,
            InternalMarkRequestDTO requestDTO
    ) {

        if (examId == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }

        if (requestDTO.getCapId() == null || requestDTO.getCapId().isBlank()) {
            throw new IllegalArgumentException("CAP ID is required");
        }

        if (requestDTO.getSubjectCode() == null || requestDTO.getSubjectCode().isBlank()) {
            throw new IllegalArgumentException("Subject code is required");
        }

        if (requestDTO.getMarks() == null) {
            throw new IllegalArgumentException("Marks are required");
        }

        boolean alreadyExists =
                repository.existsByExamIdAndCapIdAndSubjectCode(
                        examId,
                        requestDTO.getCapId(),
                        requestDTO.getSubjectCode()
                );

        if (alreadyExists) {
            throw new IllegalStateException(
                    "Internal marks already added for this subject"
            );
        }

        InternalMark mark =
                InternalMarkMapper.toEntity(requestDTO, examId);

        InternalMark saved = repository.save(mark);

        return InternalMarkMapper.toResponse(saved);
    }



    @Override
    public void uploadInternalMarksFromCsv(
            Long examId,
            MultipartFile file
    ) {

        if (examId == null) {
            throw new IllegalArgumentException("Exam ID is required");
        }

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("CSV file is required");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {


                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");

                InternalMarkRequestDTO dto = new InternalMarkRequestDTO();
                dto.setCapId(data[0].trim());
                dto.setSubjectCode(data[1].trim());
                dto.setMarks(Integer.parseInt(data[2].trim()));

                addInternalMark(examId, dto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file", e);
        }
    }

    @Override
    public List<InternalMarkResponseDTO> getMyInternalMarks(String capId) {

        if (capId == null || capId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cap ID is required");
        }

        return repository.findByCapId(capId.trim())
                .stream()
                .map(InternalMarkMapper::toResponse)
                .toList();
    }
    @Override
    public InternalMarkResponseDTO updateInternalMark(
            Long id,
            InternalMarkRequestDTO requestDTO
    ) {
        if (id == null) {
            throw new IllegalArgumentException("Internal mark ID is required");
        }

        InternalMark mark = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "InternalMark", "id", id
                        )
                );

        mark.setMarks(requestDTO.getMarks());

        InternalMark updated = repository.save(mark);

        return InternalMarkMapper.toResponse(updated);
    }
    @Override
    public void deleteInternalMark(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Internal mark ID is required");
        }

        InternalMark mark = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "InternalMark", "id", id
                        )
                );

        repository.delete(mark);
    }


}
