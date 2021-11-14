package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;

import java.util.List;

public interface FreeTimeService {

    void create(FreeTime freeTime);

    void delete(Long id);

    FreeTime getById(Long id);

    List<FreeTime> getAllFreeTimeSpecialist(Long id);
}
