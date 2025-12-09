package com.example.student_portal.student_service.repository;

import com.example.student_portal.student_service.model.UploadBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadBatchRepository extends JpaRepository<UploadBatch, UUID> {
}
