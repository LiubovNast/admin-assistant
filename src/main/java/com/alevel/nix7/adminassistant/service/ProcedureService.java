package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.procedure.ProcedureRequest;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;

import java.util.List;

public interface ProcedureService {

    ProcedureResponse create(ProcedureRequest procedure, Long SpecialistId);

    void delete(Long id);

    ProcedureResponse getById(Long id);

    List<ProcedureResponse> findAll();

    List<ProcedureResponse> findAllBySpecialist(Long id);
}
