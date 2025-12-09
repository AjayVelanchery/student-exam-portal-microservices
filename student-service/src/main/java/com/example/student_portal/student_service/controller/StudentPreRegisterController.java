package com.example.student_portal.student_service.controller;


import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import com.example.student_portal.student_service.service.StudentPreRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentPreRegisterController {

    private final StudentPreRegisterService studentService;

    @PostMapping
    public ResponseEntity<StudentPreRegisterResponse> createStudent(
            @RequestBody StudentPreRegisterRequest request) {
        StudentPreRegisterResponse response = studentService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/bulk")
    public ResponseEntity<List<StudentPreRegisterResponse>> bulkCreateStudents(
            @RequestBody List<StudentPreRegisterRequest> requestList) {
        List<StudentPreRegisterResponse> responseList = studentService.bulkCreate(requestList);
        return new ResponseEntity<>(responseList, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Upload CSV file for bulk student creation",
            description = "Upload a CSV file containing student data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid CSV file")
    })

    @PostMapping(value = "/bulk-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> bulkUpload(@RequestParam("file") MultipartFile file) {
        studentService.bulkUpload(file);
        return ResponseEntity.accepted().body("Bulk upload request received. Processing...");
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentPreRegisterResponse> getStudentById(@PathVariable Long id) {
        StudentPreRegisterResponse response = studentService.getById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cap/{capId}")
    public ResponseEntity<StudentPreRegisterResponse> getStudentByCapId(@PathVariable String capId) {
        StudentPreRegisterResponse response = studentService.getByCapId(capId);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<StudentPreRegisterResponse>> getAllStudents() {
        List<StudentPreRegisterResponse> responseList = studentService.getAll();
        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/validate/{capId}")
    public ResponseEntity<Boolean> isCapIdValid(@PathVariable String capId) {
        boolean exists = studentService.isCapIdValid(capId);
        return ResponseEntity.ok(exists);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentPreRegisterResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentPreRegisterRequest request) {
        StudentPreRegisterResponse response = studentService.update(id, request);
        return ResponseEntity.ok(response);
    }
}



