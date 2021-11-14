package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;

import java.util.List;

public interface ProcedureService {

    void create(Procedure procedure);

    void delete(Long id);

    Procedure getById(Long id);

    List<Procedure> getAll();
}
