package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;

import java.util.List;

public interface AdminService {

    AdminResponse create(AdminSaveRequest admin);

    void update(Admin admin);

    AdminResponse getById(Long id);

    void delete(Long id);

    List<AdminResponse> getAllAdmins();
}
