package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.repository.ProcedureRepository;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ProcedureResponse getById(Long id) {
        return ProcedureResponse.fromProcedure(procedureRepository.getById(id));
    }

    @Override
    public List<ProcedureResponse> findAllBySpecialist(Long id) {
        return procedureRepository.findAllBySpecialists(id).stream()
                .map(ProcedureResponse::fromProcedure)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcedureResponse> findAll() {
        return procedureRepository.findAll().stream()
                .map(ProcedureResponse::fromProcedure)
                .collect(Collectors.toList());
    }
}
