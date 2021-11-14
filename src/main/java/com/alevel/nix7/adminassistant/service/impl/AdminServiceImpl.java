package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.repository.AdminRepository;
import com.alevel.nix7.adminassistant.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminResponse create(AdminSaveRequest admin) {
        return AdminResponse.fromAdmin(save(admin));
    }

    @Override
    public void update(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public AdminResponse getById(Long id) {
        return AdminResponse.fromAdmin(adminRepository.findById(id).get());
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        return new ArrayList<>();
    }

    private Admin save(AdminSaveRequest request) {
        var admin = new Admin();
        admin.setPassword(request.password());
        admin.setFullName(request.fullName());
        admin.setLogin(request.login());
        adminRepository.save(admin);
        return admin;
    }
}
