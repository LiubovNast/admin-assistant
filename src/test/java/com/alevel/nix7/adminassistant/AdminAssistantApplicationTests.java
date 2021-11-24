package com.alevel.nix7.adminassistant;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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

        ResponseEntity<AdminResponse> adminResponseEntity = createAdmin(fullName, password, login);
        assertEquals(HttpStatus.CREATED, adminResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, adminResponseEntity.getHeaders().getContentType());

        AdminResponse responseBody = adminResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(login, responseBody.login());
        assertEquals(fullName, responseBody.fullName());
        assertEquals(Role.ROLE_ADMIN, responseBody.role());
        assertNotEquals(0, responseBody.id());
    }

    @Test
    void testCreateOwner() {
        String login = "owner1";
        String password = "password";
        String fullName = "Owner Name";

        ResponseEntity<AdminResponse> adminResponseEntity = createOwner(fullName, password, login);
        assertEquals(HttpStatus.CREATED, adminResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, adminResponseEntity.getHeaders().getContentType());

        AdminResponse responseBody = adminResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(login, responseBody.login());
        assertEquals(fullName, responseBody.fullName());
        assertEquals(Role.ROLE_OWNER, responseBody.role());
        assertNotEquals(0, responseBody.id());
    }

    private ResponseEntity<AdminResponse> createAdmin(String fullName, String password, String login) {
        String url = "http://localhost:" + port + RootPath.ADMIN;
        AdminSaveRequest request = new AdminSaveRequest(fullName, password, login);
        return rest.postForEntity(url, request, AdminResponse.class);
    }

    private ResponseEntity<AdminResponse> createOwner(String fullName, String password, String login) {
        String url = "http://localhost:" + port + RootPath.ADMIN + "/owner";
        AdminSaveRequest request = new AdminSaveRequest(fullName, password, login);
        return rest.postForEntity(url, request, AdminResponse.class);
    }
}
