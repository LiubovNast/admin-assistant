package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
