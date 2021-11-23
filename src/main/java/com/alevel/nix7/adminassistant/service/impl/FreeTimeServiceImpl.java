package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;
import com.alevel.nix7.adminassistant.repository.FreeTimeRepository;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.service.FreeTimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeTimeServiceImpl implements FreeTimeService {

    private final FreeTimeRepository freeTimeRepository;
    private final SpecialistRepository specialistRepository;

    public FreeTimeServiceImpl(FreeTimeRepository freeTimeRepository, SpecialistRepository specialistRepository) {
        this.freeTimeRepository = freeTimeRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public FreeTimeResponse create(FreeTimeRequest request, Long specialistId) {
        return FreeTimeResponse.fromTime(save(request, specialistId));
    }

    private FreeTime save(FreeTimeRequest request, Long specialistId) {
        var freeTime = new FreeTime();
        var specialist = specialistRepository.findById(specialistId).orElseThrow();
        freeTime.setFrom(request.from());
        freeTime.setTo(request.to());
        freeTime.setSpecialist(specialist);
        freeTimeRepository.save(freeTime);
        return freeTime;
    }

    @Override
    public void delete(Long id) {
        freeTimeRepository.deleteById(id);
    }

    @Override
    public FreeTime getById(Long id) {
        return freeTimeRepository.getById(id);
    }

    @Override
    public List<FreeTimeResponse> getAllFreeTimeSpecialist(Long id) {
        return freeTimeRepository.findFreeTimesBySpecialistIn(id)
                .stream().map(FreeTimeResponse::fromTime)
                .collect(Collectors.toList());
    }
}
