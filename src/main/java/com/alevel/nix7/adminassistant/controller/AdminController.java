package com.alevel.nix7.adminassistant.controller;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RootPath.ADMIN)
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse registerAdmin(@RequestBody @Valid AdminSaveRequest request) {
        return adminService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdminById(@PathVariable long id) {
        adminService.delete(id);
    }

    @GetMapping("/{id}")
    public AdminResponse getAdminById(@PathVariable long id) {
        return adminService.getById(id);
    }

    @GetMapping
    public List<AdminResponse> listAdmins() {
        return adminService.getAllAdmins();
    }
}
