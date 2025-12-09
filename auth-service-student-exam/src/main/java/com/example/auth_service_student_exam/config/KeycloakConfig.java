package com.example.auth_service_student_exam.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class KeycloakConfig {

    private final String SERVER_URL = "http://localhost:8080";   // ✔ Correct for new Keycloak
    private final String REALM = "master";                       // ✔ Admin realm
    private final String CLIENT_ID = "admin-cli";                // ✔ Admin client
    private final String USERNAME = "admin";                     // ✔ Admin username
    private final String PASSWORD = "admin";                     // ✔ Admin password

    @Bean
    public Keycloak keycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .username(USERNAME)
                .password(PASSWORD)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
