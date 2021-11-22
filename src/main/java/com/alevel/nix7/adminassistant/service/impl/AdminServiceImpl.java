package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.model.admin.AdminDetails;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.repository.AdminRepository;
import com.alevel.nix7.adminassistant.service.AdminService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
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
        return AdminResponse.fromAdmin(adminRepository.findById(id).orElseThrow());
    }

    @Override
    public AdminResponse getByLogin(String login) {
        Admin admin = adminRepository.findAdminByLogin(login);
        return admin == null ? null : AdminResponse.fromAdmin(admin);
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream().map(AdminResponse::fromAdmin).collect(Collectors.toList());
    }

    private Admin save(AdminSaveRequest request) {
        var admin = new Admin();
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setFullName(request.fullName());
        admin.setLogin(request.login());
        admin.setRole(request.role());
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByLogin(login);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin " + login + " not found");
        }
        return new AdminDetails(admin);
    }
}
