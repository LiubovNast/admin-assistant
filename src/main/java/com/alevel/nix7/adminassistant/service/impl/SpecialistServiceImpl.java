package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistResponse;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public SpecialistResponse create(SpecialistRequest request) {
        return SpecialistResponse.fromWorker(save(request));
    }

    private Specialist save(SpecialistRequest request) {
        Specialist specialist = new Specialist();
        specialist.setFullName(request.fullName());
        specialist.setLogin(request.login());
        specialist.setPassword(request.password());
        specialist.setRole(Role.ROLE_WORKER);
        specialistRepository.save(specialist);
        return specialist;
    }

    @Override
    public Specialist getSpecialistById(Long id) {
        return specialistRepository.getById(id);
    }

    @Override
    public List<SpecialistResponse> getAllSpecialist() {
        return specialistRepository.findAll().stream()
                .map(SpecialistResponse::fromWorker).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        specialistRepository.deleteById(id);
    }
}
