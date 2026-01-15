package com.examportal.student_exam_service.controller;

import com.examportal.student_exam_service.dto.InternalMarkRequestDTO;
import com.examportal.student_exam_service.dto.InternalMarkResponseDTO;
import com.examportal.student_exam_service.service.InternalMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/internal-marks")
@RequiredArgsConstructor
public class InternalMarkController {

    private final InternalMarkService service;


    @PostMapping
    public ResponseEntity<InternalMarkResponseDTO> addInternalMark(
            @RequestParam Long examId,
            @RequestBody InternalMarkRequestDTO requestDTO
    ) {
        InternalMarkResponseDTO response =
                service.addInternalMark(examId, requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadInternalMarks(
            @RequestParam Long examId,
            @RequestPart("file") MultipartFile file
    ) {
        service.uploadInternalMarksFromCsv(examId, file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Internal marks uploaded successfully");
    }

    @GetMapping("/student")
    public ResponseEntity<List<InternalMarkResponseDTO>> getMyInternalMarks(
            @RequestHeader("X-Cap-Id") String capId
    ) {
        return ResponseEntity.ok(
                service.getMyInternalMarks(capId)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<InternalMarkResponseDTO> updateInternalMark(
            @PathVariable Long id,
            @RequestBody InternalMarkRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                service.updateInternalMark(id, requestDTO)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternalMark(
            @PathVariable Long id
    ) {
        service.deleteInternalMark(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<InternalMarkResponseDTO>> getInternalMarksForAdmin(
            @RequestParam(required = false) String capId
    ) {
        return ResponseEntity.ok(
                service.getInternalMarksForAdmin(capId)
        );
    }


}
