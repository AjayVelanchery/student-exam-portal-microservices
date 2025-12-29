package com.example.student_portal.student_service.controller;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentProfileResponse;
import com.example.student_portal.student_service.dto.StudentSelfUpdateRequest;
import com.example.student_portal.student_service.service.StudentSelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/me")
@RequiredArgsConstructor
public class StudentController {

    private final StudentSelfService studentService;


    @GetMapping
    public StudentProfileResponse getMyProfile(@RequestHeader("X-Cap-Id") String capId) {
        return studentService.getMyProfile(capId);
    }

    @PutMapping
    public StudentProfileResponse updateMyProfile(@RequestHeader("X-Cap-Id") String capId,
                                                  @RequestBody StudentSelfUpdateRequest request) {
        return studentService.updateMyProfile(capId, request);
    }
}
