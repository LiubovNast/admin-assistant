package com.alevel.nix7.adminassistant;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistResponse;
import com.alevel.nix7.adminassistant.model.user.UserRequest;
import com.alevel.nix7.adminassistant.model.user.UserResponse;
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
        var login = "login";
        var password = "password";
        var fullName = "Full Name";

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
        var login = "owner1";
        var password = "password";
        var fullName = "Owner Name";

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

    @Test
    void testGetAdminById() {
        var login = "admin";
        var password = "password";
        var fullName = "Admin Name";

        var adminResponse = createAdmin(fullName, password, login).getBody();
        assertNotNull(adminResponse);

        var id = adminResponse.id();
        var urlId = getUrlAdmin() + "/" + id;


        ResponseEntity<AdminResponse> responseEntity = rest.getForEntity(urlId, AdminResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        var responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(login, responseBody.login());
        assertEquals(fullName, responseBody.fullName());
        assertEquals(id, responseBody.id());

        assertEquals(adminResponse, rest.getForEntity(urlId, AdminResponse.class).getBody());
    }

    @Test
    void testCreateWorker() {
        String login = "worker";
        String password = "password";
        String fullName = "Worker Name";

        ResponseEntity<SpecialistResponse> responseEntity = createWorker(fullName, login, password);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        SpecialistResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(login, responseBody.login());
        assertEquals(fullName, responseBody.fullName());
        assertNotEquals(0, responseBody.id());
    }

    @Test
    void testCreateUser() {
        var phone = "0991234567";
        var fullName = "User Name";

        ResponseEntity<UserResponse> responseEntity = createUser(fullName, phone);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        var responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(phone, responseBody.phone());
        assertEquals(fullName, responseBody.fullName());
        assertNotEquals(0, responseBody.id());
    }

    private ResponseEntity<AdminResponse> createAdmin(String fullName, String password, String login) {
        var url = getUrlAdmin();
        var request = new AdminSaveRequest(fullName, password, login);
        return rest.postForEntity(url, request, AdminResponse.class);
    }

    private ResponseEntity<AdminResponse> createOwner(String fullName, String password, String login) {
        var url = getUrlAdmin() + "/owner";
        var request = new AdminSaveRequest(fullName, password, login);
        return rest.postForEntity(url, request, AdminResponse.class);
    }

    private ResponseEntity<UserResponse> createUser(String fullName, String phone) {
        var url = getUrlUser();
        var request = new UserRequest(fullName, phone);
        return rest.postForEntity(url, request, UserResponse.class);
    }

    private ResponseEntity<SpecialistResponse> createWorker(String fullName, String login, String password) {
        var url = getUrlWorker();
        var request = new SpecialistRequest(fullName, login, password);
        return rest.postForEntity(url, request, SpecialistResponse.class);
    }

    private String getUrlAdmin() {
        return "http://localhost:" + port + RootPath.ADMIN;
    }

    private String getUrlWorker() {
        return "http://localhost:" + port + RootPath.WORKER;
    }

    private String getUrlUser() {
        return "http://localhost:" + port + RootPath.USER;
    }
}
