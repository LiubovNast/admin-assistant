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

@Service
public class FreeTimeServiceImpl implements FreeTimeService {

    private final FreeTimeRepository freeTimeRepository;
    private final SpecialistRepository specialistRepository;
    private static final long MINUTE = 60_000;

    public FreeTimeServiceImpl(FreeTimeRepository freeTimeRepository, SpecialistRepository specialistRepository) {
        this.freeTimeRepository = freeTimeRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public FreeTimeResponse create(FreeTimeRequest request, Long specialistId) {
        checkTime(request);
        return FreeTimeResponse.fromTime(save(request, specialistId));
    }

    @Override
    public void delete(Long id) {
        if (!freeTimeRepository.existsById(id)) {
            throw AssistantException.freeTimeNotFound(id);
        }
        freeTimeRepository.deleteById(id);
    }

    @Override
    public FreeTimeResponse getById(Long id) {
        return FreeTimeResponse.fromTime(freeTimeRepository.findById(id)
                .orElseThrow(() -> AssistantException.freeTimeNotFound(id)));
    }

    @Override
    public List<FreeTimeResponse> getAllFreeTimeSpecialist(Long id) {
        return freeTimeRepository.findAllBySpecialist(specialistRepository.findById(id)
                        .orElseThrow(() -> AssistantException.workerNotFound(id)))
                .stream().map(FreeTimeResponse::fromTime)
                .toList();
    }

    @Override
    public FreeTimeResponse getFreeTimeForRecord(Specialist specialist, Timestamp start, long duration) {
        var finish = new Timestamp(start.getTime() + duration * MINUTE);
        var freeTime = freeTimeRepository
                .findBySpecialistAndFromBeforeAndToAfter(specialist, start, finish)
                .orElseThrow(() -> AssistantException.freeTimeNotFound(specialist.getFullName()));
        return FreeTimeResponse.fromTime(freeTime);
    }

    private FreeTime save(FreeTimeRequest request, Long specialistId) {
        var freeTime = new FreeTime();
        var specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> AssistantException.workerNotFound(specialistId));
        freeTime.setFrom(request.from());
        freeTime.setTo(request.to());
        freeTime.setSpecialist(specialist);
        freeTimeRepository.save(freeTime);
        return freeTime;
    }

    private void checkTime(FreeTimeRequest request) {
        if (request.from().after(request.to())) {
            throw AssistantException.invalidTime();
        }
    }
}
