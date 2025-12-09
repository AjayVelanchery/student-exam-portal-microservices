package com.example.student_portal.student_service.service.kafka;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.mapper.StudentPreRegisterMapper;
import com.example.student_portal.student_service.model.StudentPreRegister;
import com.example.student_portal.student_service.repository.StudentPreRegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentPreRegisterConsumer {

    private final StudentPreRegisterRepository repository;

    @KafkaListener(topics = "student-pre-register-topic", groupId = "student-service-group")
    public void consume(StudentPreRegisterRequest request) {

        StudentPreRegister entity = StudentPreRegisterMapper.toEntity(request);

        entity.setStatus("REGISTERED");

        StudentPreRegister saved = repository.save(entity);

        log.info("Saved student (now REGISTERED): {}", saved.getName());
    }

}
