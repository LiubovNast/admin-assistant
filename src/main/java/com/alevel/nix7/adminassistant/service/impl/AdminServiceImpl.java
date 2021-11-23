package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.model.admin.AdminDetails;
import com.alevel.nix7.adminassistant.model.admin.AdminResponse;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.repository.AdminRepository;
import com.alevel.nix7.adminassistant.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminResponse create(AdminSaveRequest admin) {
        LOG.info("Create new Admin {}", admin.fullName());
        return AdminResponse.fromAdmin(save(admin, Role.ROLE_ADMIN));
    }

    @Override
    public AdminResponse createOwner(AdminSaveRequest admin) {
        LOG.info("Create new Owner {}", admin.fullName());
        return AdminResponse.fromAdmin(save(admin, Role.ROLE_OWNER));
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

    private Admin save(AdminSaveRequest request, Role role) {
        var admin = new Admin();
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setFullName(request.fullName());
        admin.setLogin(request.login());
        admin.setRole(role);
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
