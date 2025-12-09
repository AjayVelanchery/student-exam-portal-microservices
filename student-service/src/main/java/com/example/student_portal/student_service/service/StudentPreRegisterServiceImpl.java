package com.example.student_portal.student_service.service;


import com.example.student_portal.student_service.csv.StudentCSVParser;
import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import com.example.student_portal.student_service.mapper.StudentPreRegisterMapper;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import com.example.student_portal.student_service.service.kafka.StudentPreRegisterProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentPreRegisterServiceImpl implements StudentPreRegisterService  {

    private final StudentPreRegisterRepository repository;
    private final StudentPreRegisterProducer producer;
    private final StudentCSVParser csvParser;
    @Override
    @Transactional
    public StudentPreRegisterResponse create(StudentPreRegisterRequest request) {
        StudentPreRegister entity = StudentPreRegisterMapper.toEntity(request);
        StudentPreRegister saved = repository.save(entity);
        return StudentPreRegisterMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<StudentPreRegisterResponse> bulkCreate(List<StudentPreRegisterRequest> requestList) {


        requestList.forEach(producer::sendAsync);


        return requestList.stream()
                .map(req -> new StudentPreRegisterResponse(
                        null,
                        req.getCapId(),
                        req.getName(),
                        req.getProgramme(),
                        req.getBatch(),
                        "PENDING"
                ))
                .toList();
    }



    @Transactional
    public void bulkUpload(MultipartFile file) {
        try {
            List<StudentPreRegisterRequest> rows = csvParser.parse(file);

            rows.forEach(row -> producer.sendAsync(row));

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file");
        }
    }



    @Override
    public StudentPreRegisterResponse update(Long id, StudentPreRegisterRequest request) {
        StudentPreRegister entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentPreRegisterMapper.updateEntity(entity, request);
        StudentPreRegister updated = repository.save(entity);

        return StudentPreRegisterMapper.toResponse(updated);
    }

    @Override
    public StudentPreRegisterResponse getById(Long id) {
        StudentPreRegister entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        return StudentPreRegisterMapper.toResponse(entity);
    }

    @Override
    public List<StudentPreRegisterResponse> getAll() {
        List<StudentPreRegister> list = repository.findAll();
        return StudentPreRegisterMapper.toResponseList(list);
    }

    @Override
    public StudentPreRegisterResponse getByCapId(String capId) {
        StudentPreRegister entity = repository.findByCapId(capId)
                .orElseThrow(() -> new RuntimeException("Student with CAP ID " + capId + " not found"));
        return StudentPreRegisterMapper.toResponse(entity);
    }

    @Override
    public boolean isCapIdValid(String capId) {
        return repository.existsByCapId(capId.trim());

    }
}
