package com.example.student_portal.student_service.service.kafka;

import com.example.student_portal.student_service.dto.StudentPreRegisterRequest;
import com.example.student_portal.student_service.model.StudentPreRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentPreRegisterProducer {


    private final KafkaTemplate<String, StudentPreRegisterRequest> kafkaTemplate;

    public void sendAsync(StudentPreRegisterRequest request) {
        kafkaTemplate.send("student-pre-register-topic", request);
    }
}
