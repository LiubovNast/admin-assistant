package com.alevel.nix7.adminassistant.service.impl;

import com.alevel.nix7.adminassistant.exceptions.AssistantException;
import com.alevel.nix7.adminassistant.model.freetime.FreeTimeRequest;
import com.alevel.nix7.adminassistant.model.procedure.ProcedureResponse;
import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;
import com.alevel.nix7.adminassistant.repository.ProcedureRepository;
import com.alevel.nix7.adminassistant.repository.RecordRepository;
import com.alevel.nix7.adminassistant.repository.SpecialistRepository;
import com.alevel.nix7.adminassistant.repository.UserRepository;
import com.alevel.nix7.adminassistant.service.FreeTimeService;
import com.alevel.nix7.adminassistant.service.ProcedureService;
import com.alevel.nix7.adminassistant.service.RecordService;
import com.alevel.nix7.adminassistant.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final FreeTimeService freeTimeService;
    private final SpecialistRepository specialistRepository;
    private final ProcedureRepository procedureRepository;
    private final ProcedureService procedureService;

    private static final long MINUTE = 60_000;
    private static final long MIN_RECORD = 15;

    public RecordServiceImpl(RecordRepository recordRepository,
                             UserRepository userRepository,
                             FreeTimeService freeTimeService,
                             SpecialistRepository specialistRepository,
                             ProcedureRepository procedureRepository,
                             ProcedureService procedureService) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.freeTimeService = freeTimeService;
        this.specialistRepository = specialistRepository;
        this.procedureRepository = procedureRepository;
        this.procedureService = procedureService;
    }

    @Override
    public RecordResponse create(RecordRequest recordRequest, Long userId) {
        return save(recordRequest, userId);
    }

    @Override
    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw AssistantException.recordNotFound(id);
        }
        recordRepository.deleteById(id);
    }

    @Override
    public List<RecordResponse> getRecordsByUser(Long id) {
        return recordRepository.findRecordsByUser(userRepository.findById(id)
                        .orElseThrow(() -> AssistantException.userNotFound(id)))
                .stream().map(RecordResponse::fromRecord).toList();
    }

    private RecordResponse save(RecordRequest recordRequest, Long userId) {
        var procedure = procedureService.getById(recordRequest.procedureId());
        checkFreeTime(procedure, recordRequest.when(), procedure.duration());
        var record = new Record();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> AssistantException.userNotFound(userId));
        record.setProcedure(procedureRepository.findById(procedure.id())
                .orElseThrow(() -> AssistantException.procedureNotFound(procedure.id())));
        record.setUser(user);
        record.setWhen(recordRequest.when());
        recordRepository.save(record);
        return new RecordResponse(record.getId(), procedure.name(), recordRequest.when(), userId);
    }

    private void checkFreeTime(ProcedureResponse procedure, Timestamp when, Long duration) {
        var specialist = specialistRepository.findById(procedure.idSpecialist())
                .orElseThrow(() -> AssistantException.workerNotFound(procedure.idSpecialist()));
        var freeTime = freeTimeService.getFreeTimeForRecord(specialist, when, duration);
        freeTimeService.delete(freeTime.id());
        var start = when.getTime();
        var finish = start + duration * MINUTE;
        var period = start - freeTime.fromTime().getTime();
        if ((period / MINUTE) > MIN_RECORD) {
            freeTimeService.create(new FreeTimeRequest(freeTime.fromTime(), when), specialist.getId());
        }
        period = freeTime.toTime().getTime() - finish;
        if (period / MINUTE > MIN_RECORD) {
            freeTimeService.create(new FreeTimeRequest(new Timestamp(finish), freeTime.toTime()), specialist.getId());
        }
    }
}
