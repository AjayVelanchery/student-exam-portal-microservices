package com.example.student_portal.student_service.controller;

import com.example.student_portal.student_service.dto.StudentPreRegisterAdminResponse;
import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import com.example.student_portal.student_service.service.AdminPreRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class AdminController {

    private final AdminPreRegisterService adminService;

    @PostMapping
    public StudentPreRegisterResponse createStudent(@RequestBody StudentPreRegisterRequest request) {
        return adminService.create(request);
    }

    @PostMapping("/bulk")
    public List<StudentPreRegisterResponse> bulkCreate(@RequestBody List<StudentPreRegisterRequest> requestList) {
        return adminService.bulkCreate(requestList);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Bulk upload students via CSV file")
    public String bulkUpload(
            @Parameter(description = "CSV file to upload", required = true)
            @RequestPart("file") MultipartFile file) {
        int total = adminService.bulkUpload(file);
        return "CSV uploaded successfully. Total students processed: " + total;
    }


    @PostMapping(value = "/upload-excel", consumes = "multipart/form-data")
    @Operation(summary = "Bulk upload students via Excel file (.xlsx)")
    public String bulkUploadExcel(
            @Parameter(description = "Excel (.xlsx) file to upload", required = true)
            @RequestPart("file") MultipartFile file) {

        int total = adminService.bulkUploadExcel(file);

        return "Excel uploaded successfully. Total students processed: " + total;
    }



    @PutMapping("/{id}")
    public StudentPreRegisterResponse updateStudent(@PathVariable Long id,
                                                    @RequestBody StudentPreRegisterRequest request) {
        return adminService.update(id, request);
    }

    @GetMapping("/admin/student/{id}")
    public StudentPreRegisterAdminResponse getById(
            @PathVariable Long id) {

        return adminService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        adminService.delete(id);
    }

    @GetMapping("/admin/student/cap/{capId}")
    public StudentPreRegisterAdminResponse getByCapId(
            @PathVariable String capId) {

        return adminService.getByCapId(capId);
    }

    @GetMapping
    public List<StudentPreRegisterResponse> getAllStudents() {
        return adminService.getAll();
    }



    @GetMapping("/validate/{capId}")
    public boolean isCapIdValid(@PathVariable String capId) {
        return adminService.isCapIdValid(capId);
    }
}
