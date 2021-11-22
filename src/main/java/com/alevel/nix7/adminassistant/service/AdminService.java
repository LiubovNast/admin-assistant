package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AdminService extends UserDetailsService {

    AdminResponse create(AdminSaveRequest admin);

    void update(Admin admin);

    AdminResponse getById(Long id);

    AdminResponse getByLogin(String login);

    void delete(Long id);

    List<AdminResponse> getAllAdmins();
}
