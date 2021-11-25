package com.alevel.nix7.adminassistant.repository;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    List<Procedure> findAllBySpecialist(Specialist specialist);
}