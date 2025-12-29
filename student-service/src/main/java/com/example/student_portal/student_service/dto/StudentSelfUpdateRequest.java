package com.example.student_portal.student_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentSelfUpdateRequest {

    private String name;
    private LocalDate dob;
    private String gender;
    private String district;
    private String state;
    private String postalCode;
    private String email;
    private String mobile;
    private String parentName;
    private String parentContactNo;
    private String programme;
    private String batch;
    private String bloodGroup;
    private String community;
    private String adhaarNumber;
}
