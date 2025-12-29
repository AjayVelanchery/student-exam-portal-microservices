package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentProfileResponse;
import com.example.student_portal.student_service.dto.StudentSelfUpdateRequest;

public interface StudentSelfService {


    StudentProfileResponse getMyProfile(String capId);


    StudentProfileResponse updateMyProfile(String capId, StudentSelfUpdateRequest request);
}
