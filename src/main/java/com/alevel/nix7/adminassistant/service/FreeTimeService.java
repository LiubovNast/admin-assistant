package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;

import java.util.List;

public interface FreeTimeService {

    FreeTimeResponse create(FreeTimeRequest freeTime, Long id);

    void delete(Long id);

    FreeTime getById(Long id);

    List<FreeTimeResponse> getAllFreeTimeSpecialist(Long id);
}
