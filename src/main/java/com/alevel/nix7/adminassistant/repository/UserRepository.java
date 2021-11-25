package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    boolean existsByPhone(String phone);
}