package com.example.student_portal.student_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_pre_register")
public class StudentPreRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cap_id", nullable = false, unique = true)
    private String capId;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "gender")
    private String gender;


    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_contact_no")
    private String parentContactNo;

    @Column(name = "programme")
    private String programme;

    @Column(name = "batch")
    private String batch;


    @Column(name = "status")
    private String status = "PRE_REGISTERED";

    @Column(name = "blood_group")
    private String bloodGroup;


    @Column(name = "community")
    private String community;

    @Column(name = "adhaar_number")
    private String adhaarNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
