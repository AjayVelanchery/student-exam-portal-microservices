package com.example.auth_service_student_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapIdResponse {
    private String capId;
    private boolean valid;
    private String message;
}
