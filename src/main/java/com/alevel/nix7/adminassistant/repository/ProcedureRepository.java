package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
}