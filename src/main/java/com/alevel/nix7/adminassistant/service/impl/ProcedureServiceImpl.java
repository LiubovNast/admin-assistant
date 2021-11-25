package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureRequest;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.repository.ProcedureRepository;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final SpecialistRepository specialistRepository;
    private final long MINUTE = 60_000;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository,
                                SpecialistRepository specialistRepository) {
        this.procedureRepository = procedureRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public ProcedureResponse create(ProcedureRequest procedure, Long idSpecialist) {
        return save(procedure, idSpecialist);
    }

    private ProcedureResponse save(ProcedureRequest request, Long idSpecialist) {
        var procedure = new Procedure();
        procedure.setName(request.name());
        procedure.setDuration(request.duration() * MINUTE);
        procedure.setPrice(request.price());
        procedure.setSpecialist(specialistRepository.getById(idSpecialist));
        procedureRepository.save(procedure);
        return ProcedureResponse.fromProcedure(procedure);
    }

    @Override
    public void delete(Long id) {
        procedureRepository.deleteById(id);
    }

    @Override
    public ProcedureResponse getById(Long id) {
        return ProcedureResponse.fromProcedure(procedureRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProcedureResponse> findAllBySpecialist(Long id) {
        return procedureRepository.findAllBySpecialist(specialistRepository.getById(id))
                .stream().map(ProcedureResponse::fromProcedure).toList();
    }

    @Override
    public List<ProcedureResponse> findAll() {
        return procedureRepository.findAll().stream()
                .map(ProcedureResponse::fromProcedure)
                .toList();
    }
}
