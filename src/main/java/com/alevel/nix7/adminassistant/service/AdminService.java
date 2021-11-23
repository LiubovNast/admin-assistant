package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;

import java.util.List;

public interface AdminService {

    AdminResponse create(AdminSaveRequest admin);

    AdminResponse createOwner(AdminSaveRequest admin);

    AdminResponse getById(Long id);

    AdminResponse getByLogin(String login);

    void delete(Long id);

    List<AdminResponse> getAllAdmins();
}
