package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.exceptions.AssistantException;
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
    private static final long MINUTE = 60_000;
    private static final long MIN_DURATION = 10;
    private static final long MAX_DURATION = 240;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository,
                                SpecialistRepository specialistRepository) {
        this.procedureRepository = procedureRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public ProcedureResponse create(ProcedureRequest procedure, Long idSpecialist) {
        checkProcedure(procedure);
        return save(procedure, idSpecialist);
    }

    @Override
    public void delete(Long id) {
        if (!procedureRepository.existsById(id)) {
            throw AssistantException.procedureNotFound(id);
        }
        procedureRepository.deleteById(id);
    }

    @Override
    public ProcedureResponse getById(Long id) {
        return ProcedureResponse.fromProcedure(procedureRepository.findById(id)
                .orElseThrow(() -> AssistantException.procedureNotFound(id)));
    }

    @Override
    public List<ProcedureResponse> findAllBySpecialist(Long id) {
        return procedureRepository.findAllBySpecialist(specialistRepository.findById(id)
                        .orElseThrow(() -> AssistantException.workerNotFound(id)))
                .stream().map(ProcedureResponse::fromProcedure).toList();
    }

    @Override
    public List<ProcedureResponse> findAll() {
        return procedureRepository.findAll().stream()
                .map(ProcedureResponse::fromProcedure)
                .toList();
    }

    private void checkProcedure(ProcedureRequest procedure) {
        if (procedure.duration() < MIN_DURATION) {
            throw AssistantException.invalidDuration("Too short duration");
        }
        if (procedure.duration() > MAX_DURATION) {
            throw AssistantException.invalidDuration("Too long duration");
        }
    }

    private ProcedureResponse save(ProcedureRequest request, Long idSpecialist) {
        var procedure = new Procedure();
        procedure.setName(request.name());
        procedure.setDuration(request.duration() * MINUTE);
        procedure.setPrice(request.price());
        procedure.setSpecialist(specialistRepository.findById(idSpecialist)
                .orElseThrow(() -> AssistantException.workerNotFound(idSpecialist)));
        procedureRepository.save(procedure);
        return ProcedureResponse.fromProcedure(procedure);
    }
}
