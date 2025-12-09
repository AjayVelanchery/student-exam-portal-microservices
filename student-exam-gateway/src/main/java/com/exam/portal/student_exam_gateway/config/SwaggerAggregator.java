package com.exam.portal.student_exam_gateway.config;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SwaggerAggregator {

    private final WebClient webClient = WebClient.builder().build();

    @GetMapping("/v3/api-docs/{service}")
    public Mono<String> swagger(@PathVariable String service) {

        String url = switch (service) {
            case "auth" -> "http://AUTH-SERVICE/v3/api-docs";
            case "student" -> "http://STUDENT-SERVICE/v3/api-docs";
            default -> throw new IllegalArgumentException("Unknown service: " + service);
        };

        return webClient.get().uri(url).retrieve().bodyToMono(String.class);
    }
}
