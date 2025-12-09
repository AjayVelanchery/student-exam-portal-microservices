package com.exam.portal.student_exam_service_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer

public class StudentExamServicePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentExamServicePortalApplication.class, args);
	}

}
