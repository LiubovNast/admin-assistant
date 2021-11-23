package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistRequest;
import com.alevel.nix7.adminassistant.model.specialist.SpecialistResponse;

import java.util.List;

public interface SpecialistService {

    SpecialistResponse create(SpecialistRequest specialist);

    Specialist getSpecialistById(Long id);

    List<SpecialistResponse> getAllSpecialist();

    void delete(Long id);
}
