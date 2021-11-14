package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public void create(Specialist specialist) {
        specialistRepository.save(specialist);
    }

    @Override
    public void update(Specialist specialist) {
        specialistRepository.save(specialist);
    }

    @Override
    public Specialist getSpecialistById(Long id) {
        return specialistRepository.getById(id);
    }

    @Override
    public List<Specialist> getAllSpecialist() {
        return specialistRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        specialistRepository.deleteById(id);
    }
}
