package com.example.student_portal.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPreRegisterAdminResponse {

    private Long id;
    private String capId;
    private String name;
    private LocalDate dob;
    private String gender;

    private String email;
    private String mobile;

    private String parentName;
    private String parentContactNo;

    private String adhaarNumber;
    private String bloodGroup;
    private String community;

    private String district;
    private String state;
    private String postalCode;

    private String programme;
    private String batch;
    private String status;
}
