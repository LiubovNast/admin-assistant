package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;

import java.util.List;

public interface SpecialistService {

    void create(Specialist specialist);

    void update(Specialist specialist);

    Specialist getSpecialistById(Long id);

    List<Specialist> getAllSpecialist();

    void delete(Long id);
}
