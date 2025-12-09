package com.example.student_portal.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPreRegisterResponse {
    private Long id;
    private String capId;
    private String name;
    private String programme;
    private String batch;
    private String status;
}
