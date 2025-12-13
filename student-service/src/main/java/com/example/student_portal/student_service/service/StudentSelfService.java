package com.example.student_portal.student_service.service;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.dto.StudentProfileResponse;

public interface StudentSelfService {

    /**
     * Get the logged-in student's full profile.
     * @param capId forwarded by API Gateway
     */
    StudentProfileResponse getMyProfile(String capId);

    /**
     * Update the logged-in student's profile.
     * @param capId forwarded by API Gateway
     * @param request fields to update
     */
    StudentProfileResponse updateMyProfile(String capId, StudentPreRegisterRequest request);
}
