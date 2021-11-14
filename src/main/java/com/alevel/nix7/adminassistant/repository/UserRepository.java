package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);
}