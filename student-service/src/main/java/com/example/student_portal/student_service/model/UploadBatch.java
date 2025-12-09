package com.example.student_portal.student_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadBatch {

    @Id
    @GeneratedValue
    private UUID id;

    private int totalRows;
    private int processedRows;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
