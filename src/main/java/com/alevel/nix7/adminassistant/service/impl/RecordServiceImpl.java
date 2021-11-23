package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.model.procedure.Procedure;
import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;
import com.alevel.nix7.adminassistant.model.specialist.Specialist;
import com.alevel.nix7.adminassistant.model.user.User;
import com.alevel.nix7.adminassistant.repository.ProcedureRepository;
import com.alevel.nix7.adminassistant.repository.RecordRepository;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.repository.UserRepository;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import com.alevel.nix7.adminassistant.service.RecordService;
import com.alevel.nix7.adminassistant.service.SpecialistService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SpecialistService specialistService;
    private final SpecialistRepository specialistRepository;
    private final ProcedureService procedureService;
    private final ProcedureRepository procedureRepository;

    public RecordServiceImpl(RecordRepository recordRepository, UserService userService,
                             UserRepository userRepository, SpecialistService specialistService, SpecialistRepository specialistRepository, ProcedureService procedureService,
                             ProcedureRepository procedureRepository) {
        this.recordRepository = recordRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.specialistService = specialistService;
        this.specialistRepository = specialistRepository;
        this.procedureService = procedureService;
        this.procedureRepository = procedureRepository;
    }

    @Override
    public RecordResponse create(RecordRequest recordRequest, Long userId) {
        return save(recordRequest, userId);
    }

    private RecordResponse save(RecordRequest recordRequest, Long userId) {
        Record record = new Record();
        User user = userService.getById(userId);
        Specialist specialist = specialistService.getSpecialistById(recordRequest.specialistId());
        Procedure procedure = procedureRepository.getById(recordRequest.procedureId());
        record.setProcedure(procedure);
        record.setUser(user);
        record.setSpecialist(specialist);
        record.setWhen(recordRequest.when());
        recordRepository.save(record);
        return new RecordResponse(record.getId(), recordRequest.specialistId(),
                recordRequest.procedureId(), recordRequest.when(), userId);
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }

    @Override
    public List<RecordResponse> getRecordsBySpecialist(Long id) {
        return recordRepository.findRecordsBySpecialist(specialistRepository.getById(id)).stream()
                .map(RecordResponse::fromRecord).collect(Collectors.toList());
    }

    @Override
    public List<Record> getRecordsByUser(Long id) {
        return recordRepository.findRecordsByUser(userRepository.getById(id));
    }

    @Override
    public List<RecordResponse> getRecordsForSpecialistBetweenTime(Long specialistId, Timestamp from, Timestamp to) {
        return recordRepository.findRecordsBySpecialistAndWhenBetween(specialistRepository.getById(specialistId), from, to)
                .stream().map(RecordResponse::fromRecord)
                .collect(Collectors.toList());
    }
}
