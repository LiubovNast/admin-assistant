package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByLogin(String login);

    boolean existsByLogin(String login);
}
