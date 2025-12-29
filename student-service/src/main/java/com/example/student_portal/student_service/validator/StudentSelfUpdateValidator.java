package com.example.student_portal.student_service.validator;

import com.example.student_portal.student_service.dto.StudentSelfUpdateRequest;
import com.example.student_portal.student_service.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StudentSelfUpdateValidator {

    private static final Set<String> ALLOWED_BLOOD_GROUPS =
            Set.of("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

    public void validate(StudentSelfUpdateRequest req) {

        if (req == null) {
            throw new ValidationException("Request body cannot be empty");
        }

        if (req.getEmail() != null &&
                !req.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email");
        }

        if (req.getMobile() != null &&
                !req.getMobile().matches("\\d{10}")) {
            throw new ValidationException("Invalid mobile number");
        }

        if (req.getParentContactNo() != null &&
                !req.getParentContactNo().matches("\\d{10}")) {
            throw new ValidationException("Invalid parent contact number");
        }

        if (req.getPostalCode() != null &&
                !req.getPostalCode().matches("\\d{6}")) {
            throw new ValidationException("Invalid postal code");
        }

        if (req.getBloodGroup() != null &&
                !ALLOWED_BLOOD_GROUPS.contains(req.getBloodGroup().toUpperCase())) {
            throw new ValidationException("Invalid blood group");
        }

        if (req.getDistrict() != null && req.getDistrict().isBlank()) {
            throw new ValidationException("District cannot be empty");
        }


        if (req.getState() != null && req.getState().isBlank()) {
            throw new ValidationException("State cannot be empty");
        }
    }
}
