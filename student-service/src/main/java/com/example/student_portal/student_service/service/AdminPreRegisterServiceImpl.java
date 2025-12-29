package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.csv.StudentCSVParser;
import com.example.student_portal.student_service.csv.StudentExcelParser;
import com.example.student_portal.student_service.dto.StudentPreRegisterAdminResponse;
import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentPreRegisterResponse;
import com.example.student_portal.student_service.exception.CsvProcessingException;
import com.example.student_portal.student_service.exception.InvalidFileException;
import com.example.student_portal.student_service.exception.ResourceNotFoundException;
import com.example.student_portal.student_service.mapper.StudentPreRegisterMapper;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import com.example.student_portal.student_service.service.kafka.StudentPreRegisterProducer;
import com.example.student_portal.student_service.validation.StudentPreRegisterValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPreRegisterServiceImpl implements AdminPreRegisterService {

    private final StudentPreRegisterValidator validator;
    private final StudentExcelParser excelParser;
    private final StudentPreRegisterRepository repository;
    private final StudentPreRegisterProducer producer;
    private final StudentCSVParser csvParser;

    @Override
    @Transactional
    public StudentPreRegisterResponse create(StudentPreRegisterRequest request) {


        validator.validateForCreate(request);

        StudentPreRegister entity =
                StudentPreRegisterMapper.toEntity(request);

        StudentPreRegister saved = repository.save(entity);
        return StudentPreRegisterMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<StudentPreRegisterResponse> bulkCreate(
            List<StudentPreRegisterRequest> requestList) {

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


    @Override
    @Transactional
    public int bulkUploadExcel(MultipartFile file) {

        String filename = file.getOriginalFilename();

        if (file.isEmpty()) {
            throw new InvalidFileException("Uploaded file is empty");
        }

        if (filename == null || !filename.endsWith(".xlsx")) {
            throw new InvalidFileException(
                    "Invalid file type. Please upload an Excel (.xlsx) file"
            );
        }

        List<StudentPreRegisterRequest> rows = excelParser.parse(file);

        int rowNumber = 1;
        for (StudentPreRegisterRequest req : rows) {
            validator.validate(req, rowNumber); // ✅ bulk validation
            producer.sendAsync(req);
            rowNumber++;
        }

        return rows.size();
    }

    @Override
    @Transactional
    public int bulkUpload(MultipartFile file) {

        try {
            List<StudentPreRegisterRequest> rows = csvParser.parse(file);

            int rowNumber = 1;
            for (StudentPreRegisterRequest req : rows) {
                validator.validate(req, rowNumber); // ✅ bulk validation
                producer.sendAsync(req);
                rowNumber++;
            }

            return rows.size();

        } catch (IOException e) {
            throw new CsvProcessingException("Failed to parse CSV file", e);
        }
    }


    @Override
    @Transactional
    public StudentPreRegisterResponse update(
            Long id,
            StudentPreRegisterRequest request) {

        StudentPreRegister entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "StudentPreRegister", "id", id
                        ));

        validator.validateForUpdate(request, entity);

        StudentPreRegisterMapper.updateEntityForAdmin(entity, request);

        return StudentPreRegisterMapper.toResponse(entity);
    }


    @Override
    public StudentPreRegisterAdminResponse getById(Long id) {

        StudentPreRegister entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "StudentPreRegister", "id", id
                        ));

        return StudentPreRegisterMapper.toAdminResponse(entity);
    }


    @Override
    public List<StudentPreRegisterResponse> getAll() {
        return StudentPreRegisterMapper
                .toResponseList(repository.findAll());
    }


    @Override
    public StudentPreRegisterAdminResponse getByCapId(String capId) {

        StudentPreRegister entity = repository.findByCapId(capId.trim())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "StudentPreRegister", "capId", capId
                        ));

        return StudentPreRegisterMapper.toAdminResponse(entity);
    }


    @Override
    public boolean isCapIdValid(String capId) {
        return repository.existsByCapId(capId.trim());
    }
}
