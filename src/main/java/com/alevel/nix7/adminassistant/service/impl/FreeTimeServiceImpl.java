package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.repository.FreeTimeRepository;
import com.alevel.nix7.adminassistant.service.FreeTimeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeTimeServiceImpl implements FreeTimeService {

    private final FreeTimeRepository freeTimeRepository;

    public FreeTimeServiceImpl(FreeTimeRepository freeTimeRepository) {
        this.freeTimeRepository = freeTimeRepository;
    }

    @Override
    public void create(FreeTime freeTime) {
        freeTimeRepository.save(freeTime);
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
    public List<FreeTime> getAllFreeTimeSpecialist(Long id) {
        return freeTimeRepository.findFreeTimesBySpecialist(id);
    }
}
