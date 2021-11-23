package com.alevel.nix7.adminassistant.service;

import com.alevel.nix7.adminassistant.model.record.Record;
import com.alevel.nix7.adminassistant.model.record.RecordRequest;
import com.alevel.nix7.adminassistant.model.record.RecordResponse;

import java.sql.Timestamp;
import java.util.List;

public interface RecordService {

    RecordResponse create(RecordRequest record, Long userId);

    void delete(Long id);

    List<RecordResponse> getRecordsBySpecialist(Long id);

    List<Record> getRecordsByUser(Long id);

    List<RecordResponse> getRecordsForSpecialistBetweenTime(Long specialistId, Timestamp from, Timestamp to);
}
