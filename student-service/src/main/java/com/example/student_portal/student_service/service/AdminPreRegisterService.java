package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminPreRegisterService {

    StudentPreRegisterResponse create(StudentPreRegisterRequest request);

    List<StudentPreRegisterResponse> bulkCreate(List<StudentPreRegisterRequest> requestList);

    StudentPreRegisterResponse update(Long id, StudentPreRegisterRequest request);

    StudentPreRegisterResponse getById(Long id);

    List<StudentPreRegisterResponse> getAll();

    StudentPreRegisterResponse getByCapId(String capId);

    boolean isCapIdValid(String capId);

    int bulkUpload(MultipartFile file);
}
