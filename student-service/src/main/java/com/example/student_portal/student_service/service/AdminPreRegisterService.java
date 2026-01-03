package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentPreRegisterAdminResponse;
import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminPreRegisterService {

    StudentPreRegisterResponse create(StudentPreRegisterRequest request);

    List<StudentPreRegisterResponse> bulkCreate(List<StudentPreRegisterRequest> requestList);
    int bulkUploadExcel(MultipartFile file);
    StudentPreRegisterResponse update(Long id, StudentPreRegisterRequest request);

    StudentPreRegisterAdminResponse getById(Long id);

    List<StudentPreRegisterResponse> getAll();

    StudentPreRegisterAdminResponse getByCapId(String capId);

    boolean isCapIdValid(String capId);

    int bulkUpload(MultipartFile file);

    boolean delete(Long id);

}
