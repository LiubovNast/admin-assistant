package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;

import java.util.List;

public interface ProcedureService {

    void create(Procedure procedure);

    void delete(Long id);

    ProcedureResponse getById(Long id);

    List<ProcedureResponse> findAll();

    List<ProcedureResponse> findAllBySpecialist(Long id);
}
