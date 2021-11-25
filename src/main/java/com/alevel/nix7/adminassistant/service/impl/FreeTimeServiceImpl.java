package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.exceptions.AssistantException;
import com.alevel.nix7.adminassistant.model.freetime.FreeTime;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeResponse;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.repository.FreeTimeRepository;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.service.FreeTimeService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        var specialist = specialistRepository.getById(specialistId);
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
    public FreeTimeResponse getById(Long id) {
        return FreeTimeResponse.fromTime(freeTimeRepository.getById(id));
    }

    @Override
    public List<FreeTimeResponse> getAllFreeTimeSpecialist(Long id) {
        return freeTimeRepository.findAllBySpecialist(specialistRepository.getById(id))
                .stream().map(FreeTimeResponse::fromTime)
                .toList();
    }

    @Override
    public FreeTimeResponse getFreeTimeForRecord(Specialist specialist, Timestamp start, long duration) {
        var finish = new Timestamp(start.getTime() + duration);
        var freeTime = freeTimeRepository.findBySpecialistAndFromBeforeAndToAfter(specialist, start, finish);
        if (freeTime == null) {
            throw AssistantException.freeTimeNotFound(specialist.getFullName());
        }
        return FreeTimeResponse.fromTime(freeTime);
    }
}
