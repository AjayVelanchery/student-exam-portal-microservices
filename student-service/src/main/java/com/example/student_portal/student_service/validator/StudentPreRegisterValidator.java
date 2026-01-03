package com.example.student_portal.student_service.validator;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.exception.ValidationException;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StudentPreRegisterValidator {

    private final StudentPreRegisterRepository repository;

    private static final Set<String> ALLOWED_GENDERS =
            Set.of("MALE", "FEMALE", "OTHER");

    private static final Set<String> ALLOWED_BLOOD_GROUPS =
            Set.of("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");


    private void validateCommon(StudentPreRegisterRequest req) {

        if (req.getCapId() == null || !req.getCapId().matches("CAP\\d{4,8}")) {
            throw new ValidationException("Invalid CAP ID");
        }

        if (req.getName() == null || req.getName().length() < 3) {
            throw new ValidationException("Invalid student name");
        }

        if (req.getDob() == null || req.getDob().isAfter(LocalDate.now())) {
            throw new ValidationException("Invalid date of birth");
        }

        if (req.getGender() == null
                || !ALLOWED_GENDERS.contains(req.getGender().toUpperCase())) {
            throw new ValidationException("Invalid gender");
        }

        if (req.getEmail() == null ||
                !req.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email");
        }

        if (req.getMobile() == null || !req.getMobile().matches("\\d{10}")) {
            throw new ValidationException("Invalid mobile number");
        }

        if (req.getParentContactNo() == null
                || !req.getParentContactNo().matches("\\d{10}")) {
            throw new ValidationException("Invalid parent contact number");
        }

        if (req.getAdhaarNumber() == null
                || !req.getAdhaarNumber().matches("\\d{12}")) {
            throw new ValidationException("Invalid Aadhaar number");
        }

        if (req.getPostalCode() == null
                || !req.getPostalCode().matches("\\d{6}")) {
            throw new ValidationException("Invalid postal code");
        }

        if (req.getBloodGroup() == null
                || !ALLOWED_BLOOD_GROUPS.contains(req.getBloodGroup())) {
            throw new ValidationException("Invalid blood group");
        }

        if (req.getProgramme() == null || req.getProgramme().isBlank()) {
            throw new ValidationException("Programme cannot be empty");
        }

        if (req.getBatch() == null
                || !req.getBatch().matches("\\d{4}-\\d{4}")) {
            throw new ValidationException(
                    "Invalid batch year. Expected format: YYYY-YYYY"
            );
        }
    }

    public void validate(StudentPreRegisterRequest req, int row) {
        try {
            validateCommon(req);

            if (repository.existsByCapId(req.getCapId())) {
                throw new ValidationException(
                        "Duplicate CAP ID: " + req.getCapId()
                );
            }

        } catch (ValidationException ex) {
            throw new ValidationException(
                    "Row " + row + ": " + ex.getMessage()
            );
        }
    }


    public void validateForCreate(StudentPreRegisterRequest req) {

        validateCommon(req);

        if (repository.existsByCapId(req.getCapId())) {
            throw new ValidationException(
                    "Duplicate CAP ID: " + req.getCapId()
            );
        }
    }


    public void validateForUpdate(
            StudentPreRegisterRequest req,
            StudentPreRegister existing
    ) {

        validateCommon(req);

        if (!existing.getCapId().equals(req.getCapId())
                && repository.existsByCapId(req.getCapId())) {
            throw new ValidationException(
                    "Duplicate CAP ID: " + req.getCapId()
            );
        }
    }
}
