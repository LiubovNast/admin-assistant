package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;

import java.sql.Timestamp;
import java.util.List;

public interface FreeTimeService {

    FreeTimeResponse create(FreeTimeRequest freeTime, Long id);

    void delete(Long id);

    FreeTimeResponse getById(Long id);

    List<FreeTimeResponse> getAllFreeTimeSpecialist(Long id);

    FreeTimeResponse getFreeTimeForRecord(Specialist specialist, Timestamp start, long duration);
}
