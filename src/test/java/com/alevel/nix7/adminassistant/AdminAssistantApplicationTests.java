package com.alevel.nix7.adminassistant;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"h2db"})
class AdminAssistantApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void contextLoads() {
        assertNotNull(rest);
        assertNotEquals(0, port);
    }

    @Test
    void testCreateAdmin() {
        String login = "login";
        String password = "password";
        String fullName = "Full Name";

        ResponseEntity<AdminResponse> adminResponseEntity = createAdmin(fullName, password, login, Role.ROLE_ADMIN);

       // assertEquals(MediaType.APPLICATION_JSON, adminResponseEntity.getHeaders().getContentType());

    }

    private ResponseEntity<AdminResponse> createAdmin(String fullName, String password, String login, Role role) {
        String url = "http://localhost:" + port + "/admin";
        AdminSaveRequest request = new AdminSaveRequest(fullName, password, login, role);
        return rest.postForEntity(url, request, AdminResponse.class);
    }
}
