package com.exam.portal.student_exam_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        // Allow Swagger + OpenAPI endpoints
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/auth-service/v3/api-docs",
                                "/api/student-service/v3/api-docs",
                                "/webjars/swagger-ui/**",   // <--- allow Swagger UI JS/CSS
                                "/swagger-ui/index.html"     // <--- new WebFlux path
                        ).permitAll()


                        // Allow Eureka dashboard
                        .pathMatchers("/eureka/**").permitAll()

                        // Allow unauthenticated access to Auth microservice endpoints (login/signup)
                        .pathMatchers("/api/auth-service/**").permitAll()

                        // Secure student endpoints
                        .pathMatchers("/api/student-service/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/student-service/**").hasAnyRole("STUDENT","ADMIN")

                        // Other endpoints require authentication
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter()); // convert roles from Keycloak
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}
