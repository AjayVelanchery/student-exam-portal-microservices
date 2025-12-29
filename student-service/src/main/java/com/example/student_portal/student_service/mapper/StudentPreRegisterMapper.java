package com.example.student_portal.student_service.mapper;

import com.example.student_portal.student_service.dto.*;
import com.example.student_portal.student_service.model.StudentPreRegister;

import java.util.List;

public class StudentPreRegisterMapper {


    public static StudentPreRegister toEntity(StudentPreRegisterRequest request) {
        if (request == null) return null;

        StudentPreRegister entity = new StudentPreRegister();
        entity.setCapId(request.getCapId());
        entity.setName(request.getName());
        entity.setDob(request.getDob());
        entity.setGender(request.getGender());
        entity.setDistrict(request.getDistrict());
        entity.setState(request.getState());
        entity.setPostalCode(request.getPostalCode());
        entity.setEmail(request.getEmail());
        entity.setMobile(request.getMobile());
        entity.setParentName(request.getParentName());
        entity.setParentContactNo(request.getParentContactNo());
        entity.setProgramme(request.getProgramme());
        entity.setBatch(request.getBatch());
        entity.setBloodGroup(request.getBloodGroup());
        entity.setCommunity(request.getCommunity());
        entity.setAdhaarNumber(request.getAdhaarNumber());

        return entity;
    }


    public static StudentPreRegisterResponse toResponse(StudentPreRegister entity) {
        if (entity == null) return null;

        return new StudentPreRegisterResponse(
                entity.getId(),
                entity.getCapId(),
                entity.getName(),
                entity.getProgramme(),
                entity.getBatch(),
                entity.getStatus()
        );
    }
    public static void updateEntityforStudent(StudentPreRegister entity, StudentSelfUpdateRequest request) {
        if (entity == null || request == null) return;

        entity.setName(request.getName());
        entity.setDob(request.getDob());
        entity.setGender(request.getGender());
        entity.setDistrict(request.getDistrict());
        entity.setState(request.getState());
        entity.setPostalCode(request.getPostalCode());
        entity.setEmail(request.getEmail());
        entity.setMobile(request.getMobile());
        entity.setParentName(request.getParentName());
        entity.setParentContactNo(request.getParentContactNo());
        entity.setProgramme(request.getProgramme());
        entity.setBatch(request.getBatch());
        entity.setBloodGroup(request.getBloodGroup());
        entity.setCommunity(request.getCommunity());
        entity.setAdhaarNumber(request.getAdhaarNumber());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
    }


    public static List<StudentPreRegisterResponse> toResponseList(List<StudentPreRegister> list) {
        return list.stream()
                .map(StudentPreRegisterMapper::toResponse)
                .toList();
    }

    public static StudentProfileResponse toProfileResponse(StudentPreRegister entity) {
        return new StudentProfileResponse(
                entity.getId(),
                entity.getName(),
                entity.getDob(),
                entity.getGender(),
                entity.getDistrict(),
                entity.getState(),
                entity.getPostalCode(),
                entity.getEmail(),
                entity.getMobile(),
                entity.getParentName(),
                entity.getParentContactNo(),
                entity.getProgramme(),
                entity.getBatch(),
                entity.getBloodGroup(),
                entity.getCommunity(),
                entity.getAdhaarNumber()

        );
    }public static void updateEntityForAdmin(
            StudentPreRegister entity,
            StudentPreRegisterRequest request
    ) {
        if (entity == null || request == null) return;

        entity.setCapId(request.getCapId());
        entity.setName(request.getName());
        entity.setDob(request.getDob());
        entity.setGender(request.getGender());
        entity.setDistrict(request.getDistrict());
        entity.setState(request.getState());
        entity.setPostalCode(request.getPostalCode());
        entity.setEmail(request.getEmail());
        entity.setMobile(request.getMobile());
        entity.setParentName(request.getParentName());
        entity.setParentContactNo(request.getParentContactNo());
        entity.setProgramme(request.getProgramme());
        entity.setBatch(request.getBatch());
        entity.setBloodGroup(request.getBloodGroup());
        entity.setCommunity(request.getCommunity());
        entity.setAdhaarNumber(request.getAdhaarNumber());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
    }
    public static StudentPreRegisterAdminResponse toAdminResponse(
            StudentPreRegister entity) {

        StudentPreRegisterAdminResponse res =
                new StudentPreRegisterAdminResponse();

        res.setId(entity.getId());
        res.setCapId(entity.getCapId());
        res.setName(entity.getName());
        res.setDob(entity.getDob());
        res.setGender(entity.getGender());
        res.setEmail(entity.getEmail());
        res.setMobile(entity.getMobile());
        res.setParentName(entity.getParentName());
        res.setParentContactNo(entity.getParentContactNo());
        res.setAdhaarNumber(entity.getAdhaarNumber());
        res.setBloodGroup(entity.getBloodGroup());
        res.setCommunity(entity.getCommunity());
        res.setDistrict(entity.getDistrict());
        res.setState(entity.getState());
        res.setPostalCode(entity.getPostalCode());
        res.setProgramme(entity.getProgramme());
        res.setBatch(entity.getBatch());
        res.setStatus(entity.getStatus());

        return res;
    }


}
