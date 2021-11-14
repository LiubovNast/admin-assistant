package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.repository.ProcedureRepository;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    @Override
    public void create(Procedure procedure) {
        procedureRepository.save(procedure);
    }

    @Override
    public void delete(Long id) {
        procedureRepository.deleteById(id);
    }

    @Override
    public Procedure getById(Long id) {
        return procedureRepository.getById(id);
    }

    @Override
    public List<Procedure> getAll() {
        return procedureRepository.findAll();
    }
}
