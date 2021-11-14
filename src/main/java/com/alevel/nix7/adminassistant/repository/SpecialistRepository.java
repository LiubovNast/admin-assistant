package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
}